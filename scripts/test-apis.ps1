<# UTF-8 with BOM #>
# Ripple API Test Script
param(
    [string]$BaseUrl = "http://localhost:8883/ripple",
    [int]$StartupTimeoutSec = 120
)

# Fix PowerShell encoding issues
chcp 65001 | Out-Null
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
[Console]::InputEncoding = [System.Text.Encoding]::UTF8
$OutputEncoding = [System.Text.Encoding]::UTF8
$ErrorActionPreference = "Continue"

$Passed = 0
$Failed = 0
$Skipped = 0
$Token = $null
$AdminUserId = "07251713305620000"
$RoleId = "role_001"
$MenuId = "menu_001"

function Write-Title($text) {
    Write-Host ""
    Write-Host "=== $text ===" -ForegroundColor Yellow
}

function Test-Api {
    param(
        [string]$Name,
        [scriptblock]$Action,
        [switch]$SkipOnNoToken
    )
    if ($SkipOnNoToken -and -not $Token) {
        Write-Host "[SKIP] $Name (No Token)" -ForegroundColor DarkYellow
        $script:Skipped++
        return
    }
    try {
        & $Action
        Write-Host "[PASS] $Name" -ForegroundColor Green
        $script:Passed++
    } catch {
        $msg = $_.Exception.Message
        if ($_.ErrorDetails.Message) { $msg = $_.ErrorDetails.Message }
        Write-Host "[FAIL] $Name" -ForegroundColor Red
        Write-Host "       $msg" -ForegroundColor DarkRed
        $script:Failed++
    }
}

function Wait-BackendReady {
    Write-Host "Waiting for backend ($BaseUrl) ..." -ForegroundColor Cyan
    $deadline = (Get-Date).AddSeconds($StartupTimeoutSec)
    while ((Get-Date) -lt $deadline) {
        try {
            $h = Invoke-RestMethod -Uri "$BaseUrl/actuator/health" -Method Get -TimeoutSec 3
            if ($h.status -eq "UP") {
                Write-Host "Backend ready (health=UP)" -ForegroundColor Green
                return $true
            }
        } catch { }
        Start-Sleep -Seconds 3
    }
    throw "Backend not ready within ${StartupTimeoutSec}s"
}

function Get-AuthHeaders {
    return @{ "Authorization" = "Bearer $Token" }
}

function Assert-ApiSuccess($res, [string]$hint = "") {
    if ($null -eq $res) { throw "Null response $hint" }
    if ($res.PSObject.Properties.Name -contains "success") {
        if (-not $res.success) {
            $errMsg = if ($res.message) { $res.message } else { "API returned failure $hint" }
            throw $errMsg
        }
    }
}

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Ripple Backend API Test Suite" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

Wait-BackendReady

# --- Public APIs ---
Write-Title "Public APIs"

Test-Api "GET /actuator/health" {
    $r = Invoke-RestMethod -Uri "$BaseUrl/actuator/health" -Method Get
    if ($r.status -ne "UP") { throw "status=$($r.status)" }
}

Test-Api "GET /actuator/info" {
    Invoke-RestMethod -Uri "$BaseUrl/actuator/info" -Method Get | Out-Null
}

Test-Api "GET /framework/info" {
    $r = Invoke-RestMethod -Uri "$BaseUrl/framework/info" -Method Get
    Assert-ApiSuccess $r
}

Test-Api "GET /login/check (not logged in)" {
    $r = Invoke-RestMethod -Uri "$BaseUrl/login/check" -Method Get
    if ($r.success) { throw "Should return not logged in" }
}

Test-Api "POST /login/check (login)" {
    $body = @{ account = "admin"; password = "123" }
    $r = Invoke-RestMethod -Uri "$BaseUrl/login/check" -Method Post -Body $body -ContentType "application/x-www-form-urlencoded"
    Assert-ApiSuccess $r
    $script:Token = $r.data.token
    if (-not $Token) { throw "Token not received" }
}

Test-Api "Protected endpoint without token should be blocked" {
    try {
        Invoke-RestMethod -Uri "$BaseUrl/user/findAllAnnotation" -Method Post -ErrorAction Stop | Out-Null
        throw "Should be blocked by JWT"
    } catch {
        if ($_.Exception.Message -match "Should be") { throw }
    }
}

# --- Legacy /user APIs ---
Write-Title "User APIs /user"

$H = { Get-AuthHeaders }

Test-Api "POST /user/findAllAnnotation" -SkipOnNoToken {
    $r = Invoke-RestMethod -Uri "$BaseUrl/user/findAllAnnotation" -Method Post -Headers (& $H)
    if ($r.code -ne "1") { throw "code=$($r.code)" }
}

Test-Api "POST /user/findAllXml" -SkipOnNoToken {
    $r = Invoke-RestMethod -Uri "$BaseUrl/user/findAllXml" -Method Post -Headers (& $H)
    if ($r.code -ne "1") { throw "code=$($r.code)" }
}

Test-Api "POST /user/findAllBase" -SkipOnNoToken {
    $body = @{ name = "" }
    $r = Invoke-RestMethod -Uri "$BaseUrl/user/findAllBase" -Method Post -Headers (& $H) -Body $body -ContentType "application/x-www-form-urlencoded"
    if ($r.code -ne "1") { throw "code=$($r.code)" }
}

Test-Api "POST /user/findAllByMyPlus" -SkipOnNoToken {
    $body = @{ name = "" }
    $r = Invoke-RestMethod -Uri "$BaseUrl/user/findAllByMyPlus" -Method Post -Headers (& $H) -Body $body -ContentType "application/x-www-form-urlencoded"
    if ($r.code -ne "1") { throw "code=$($r.code)" }
}

Test-Api "POST /user/findUserByName" -SkipOnNoToken {
    $r = Invoke-RestMethod -Uri "$BaseUrl/user/findUserByName?userName=admin" -Method Post -Headers (& $H)
    if (-not $r.account) { throw "user not found" }
}

Test-Api "POST /user/findPage" -SkipOnNoToken {
    $body = @{ name = ""; "age" = "0"; pageIndex = "1"; pageSize = "10" }
    $r = Invoke-RestMethod -Uri "$BaseUrl/user/findPage" -Method Post -Headers (& $H) -Body $body -ContentType "application/x-www-form-urlencoded"
    if ($r.code -ne "1") { throw "code=$($r.code)" }
}

# --- Sequence ---
Write-Title "Sequence API /sequence"

Test-Api "POST /sequence/list" -SkipOnNoToken {
    $body = @{ type = "USER" }
    $r = Invoke-RestMethod -Uri "$BaseUrl/sequence/list" -Method Post -Headers (& $H) -Body $body -ContentType "application/x-www-form-urlencoded"
    if ($r.code -ne "1") { throw "code=$($r.code)" }
}

# --- Admin /admin/user ---
Write-Title "Admin APIs /admin/user"

Test-Api "GET /admin/user/info" -SkipOnNoToken {
    $r = Invoke-RestMethod -Uri "$BaseUrl/admin/user/info" -Method Get -Headers (& $H)
    Assert-ApiSuccess $r
}

Test-Api "GET /admin/user/list" -SkipOnNoToken {
    $r = Invoke-RestMethod -Uri "$BaseUrl/admin/user/list" -Method Get -Headers (& $H)
    Assert-ApiSuccess $r
}

Test-Api "GET /admin/user/roles" -SkipOnNoToken {
    $r = Invoke-RestMethod -Uri "$BaseUrl/admin/user/roles" -Method Get -Headers (& $H)
    Assert-ApiSuccess $r
}

Test-Api "GET /admin/user/{id}" -SkipOnNoToken {
    $r = Invoke-RestMethod -Uri "$BaseUrl/admin/user/$AdminUserId" -Method Get -Headers (& $H)
    Assert-ApiSuccess $r
}

# --- Admin /admin/role ---
Write-Title "Admin APIs /admin/role"

Test-Api "GET /admin/role/list" -SkipOnNoToken {
    $r = Invoke-RestMethod -Uri "$BaseUrl/admin/role/list" -Method Get -Headers (& $H)
    Assert-ApiSuccess $r
}

Test-Api "GET /admin/role/menus" -SkipOnNoToken {
    $r = Invoke-RestMethod -Uri "$BaseUrl/admin/role/menus" -Method Get -Headers (& $H)
    Assert-ApiSuccess $r
}

Test-Api "GET /admin/role/permissions" -SkipOnNoToken {
    $r = Invoke-RestMethod -Uri "$BaseUrl/admin/role/permissions" -Method Get -Headers (& $H)
    Assert-ApiSuccess $r
}

Test-Api "GET /admin/role/{id}" -SkipOnNoToken {
    $r = Invoke-RestMethod -Uri "$BaseUrl/admin/role/$RoleId" -Method Get -Headers (& $H)
    Assert-ApiSuccess $r
}

# --- Admin /admin/menu ---
Write-Title "Admin APIs /admin/menu"

Test-Api "GET /admin/menu/tree" -SkipOnNoToken {
    $r = Invoke-RestMethod -Uri "$BaseUrl/admin/menu/tree" -Method Get -Headers (& $H)
    Assert-ApiSuccess $r
}

Test-Api "GET /admin/menu/list" -SkipOnNoToken {
    $r = Invoke-RestMethod -Uri "$BaseUrl/admin/menu/list" -Method Get -Headers (& $H)
    Assert-ApiSuccess $r
}

Test-Api "GET /admin/menu/{id}" -SkipOnNoToken {
    $r = Invoke-RestMethod -Uri "$BaseUrl/admin/menu/$MenuId" -Method Get -Headers (& $H)
    Assert-ApiSuccess $r
}

# --- Write operations ---
Write-Title "Write Operations (CRUD)"

$testRoleId = $null
$testMenuId = $null

Test-Api "POST /admin/role/add + update + delete" -SkipOnNoToken {
    $roleBody = @{
        name = "API Test Role"
        code = "API_TEST_ROLE_$(Get-Date -Format 'HHmmss')"
        description = "api test"
        flag = "enabled"
    } | ConvertTo-Json
    $add = Invoke-RestMethod -Uri "$BaseUrl/admin/role/add" -Method Post -Headers (& $H) -Body $roleBody -ContentType "application/json; charset=utf-8"
    Assert-ApiSuccess $add
    $list = Invoke-RestMethod -Uri "$BaseUrl/admin/role/list" -Method Get -Headers (& $H)
    $created = $list.data | Where-Object { $_.code -like "API_TEST_ROLE_*" } | Select-Object -First 1
    if (-not $created) { throw "created role not found" }
    $script:testRoleId = $created.id
    $roleBody2 = ($created | ConvertTo-Json -Depth 5)
    $upd = Invoke-RestMethod -Uri "$BaseUrl/admin/role/update" -Method Post -Headers (& $H) -Body $roleBody2 -ContentType "application/json; charset=utf-8"
    Assert-ApiSuccess $upd
    $del = Invoke-RestMethod -Uri "$BaseUrl/admin/role/delete?id=$testRoleId" -Method Post -Headers (& $H)
    Assert-ApiSuccess $del
}

Test-Api "POST /admin/menu/add + update + delete" -SkipOnNoToken {
    $code = "api_test_menu_$(Get-Date -Format 'HHmmss')"
    $menuBody = @{
        parentId = ""
        name = "API Test Menu"
        path = "/api-test"
        component = "views/api-test"
        icon = "test"
        sortNo = 99
        type = "menu"
        permission = $code
        flag = "enabled"
    } | ConvertTo-Json
    $add = Invoke-RestMethod -Uri "$BaseUrl/admin/menu/add" -Method Post -Headers (& $H) -Body $menuBody -ContentType "application/json; charset=utf-8"
    Assert-ApiSuccess $add
    $list = Invoke-RestMethod -Uri "$BaseUrl/admin/menu/list" -Method Get -Headers (& $H)
    $created = $list.data | Where-Object { $_.permission -eq $code } | Select-Object -First 1
    if (-not $created) { throw "created menu not found" }
    $script:testMenuId = $created.id
    $upd = Invoke-RestMethod -Uri "$BaseUrl/admin/menu/update" -Method Post -Headers (& $H) -Body ($created | ConvertTo-Json -Depth 5) -ContentType "application/json; charset=utf-8"
    Assert-ApiSuccess $upd
    $del = Invoke-RestMethod -Uri "$BaseUrl/admin/menu/delete?id=$testMenuId" -Method Post -Headers (& $H)
    Assert-ApiSuccess $del
}

Test-Api "POST /admin/role/assignMenus + assignPermissions" -SkipOnNoToken {
    $menuIds = @("menu_001", "menu_005") | ConvertTo-Json
    $a1 = Invoke-RestMethod -Uri "$BaseUrl/admin/role/assignMenus?roleId=$RoleId" -Method Post -Headers (& $H) -Body $menuIds -ContentType "application/json; charset=utf-8"
    Assert-ApiSuccess $a1
    $permIds = @("perm_001", "perm_005") | ConvertTo-Json
    $a2 = Invoke-RestMethod -Uri "$BaseUrl/admin/role/assignPermissions?roleId=$RoleId" -Method Post -Headers (& $H) -Body $permIds -ContentType "application/json; charset=utf-8"
    Assert-ApiSuccess $a2
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Test Summary: Passed $Passed | Failed $Failed | Skipped $Skipped" -ForegroundColor $(if ($Failed -eq 0) { "Green" } else { "Yellow" })
Write-Host "========================================" -ForegroundColor Cyan

if ($Failed -gt 0) { exit 1 }
exit 0
