<# UTF-8 with BOM #>
# Ripple Frontend-Backend Integration Test Script
param(
    [string]$BackendUrl = "http://localhost:8883/ripple",
    [string]$FrontendUrl = "http://localhost:3001",
    [string]$TestUsername = "admin",
    [string]$TestPassword = "123"
)

# Fix PowerShell encoding issues
chcp 65001 | Out-Null
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
[Console]::InputEncoding = [System.Text.Encoding]::UTF8
$OutputEncoding = [System.Text.Encoding]::UTF8
$ErrorActionPreference = "Continue"

$passed = 0
$failed = 0
$skipped = 0

function Test-Endpoint {
    param([string]$Name, [string]$Method, [string]$Url, [string]$Body = $null, [hashtable]$Headers = @{})
    
    Write-Host "`n[$Method] $Name" -ForegroundColor Cyan
    Write-Host "  URL: $Url" -ForegroundColor Gray
    
    try {
        $params = @{
            Uri = $Url
            Method = $Method
            ContentType = if ($Body -match "=") { "application/x-www-form-urlencoded" } else { "application/json" }
            Headers = $Headers
            TimeoutSec = 10
        }
        
        if ($Body) {
            $params.Body = $Body
        }
        
        $response = Invoke-RestMethod @params
        
        Write-Host "  [PASS] Response received" -ForegroundColor Green
        return @{ Success = $true; Data = $response }
    }
    catch {
        Write-Host "  [FAIL] $($_.Exception.Message)" -ForegroundColor Red
        return @{ Success = $false; Error = $_.Exception.Message }
    }
}

Write-Host "========================================" -ForegroundColor Magenta
Write-Host "Ripple Frontend-Backend Integration Test" -ForegroundColor Magenta
Write-Host "========================================" -ForegroundColor Magenta
Write-Host "Backend: $BackendUrl" -ForegroundColor Yellow
Write-Host "Frontend: $FrontendUrl" -ForegroundColor Yellow
Write-Host ""

# 1. Test Backend Health
Write-Host "`n=== 1. Backend Health Check ===" -ForegroundColor Cyan
$result = Test-Endpoint -Name "Backend Health" -Method "GET" -Url "$BackendUrl/actuator/health"
if ($result.Success) { $passed++ } else { $failed++ }

# 2. Test Login
Write-Host "`n=== 2. User Login Test ===" -ForegroundColor Cyan

# Use form data instead of JSON
$loginBody = "account=$TestUsername&password=$TestPassword"

$result = Test-Endpoint -Name "User Login" -Method "POST" -Url "$BackendUrl/login/check" -Body $loginBody
if ($result.Success) { $passed++ } else { $failed++ }

# 3. Get JWT Token
$token = $null
if ($result.Success -and $result.Data.data.token) {
    $token = $result.Data.data.token
    Write-Host "  Token obtained: $($token.Substring(0, [Math]::Min(30, $token.Length)))..." -ForegroundColor Yellow
}

# 4. Test Authenticated Endpoints
Write-Host "`n=== 3. Authenticated User API Tests ===" -ForegroundColor Cyan

$headers = @{}
if ($token) {
    $headers["Authorization"] = "Bearer $token"
}

# User endpoints
$userEndpoints = @(
    @{ Name = "Get User Info"; Method = "GET"; Url = "$BackendUrl/admin/user/info" },
    @{ Name = "Get User List"; Method = "GET"; Url = "$BackendUrl/admin/user/list" },
    @{ Name = "Get User Roles"; Method = "GET"; Url = "$BackendUrl/admin/user/roles" },
    @{ Name = "Get User by ID"; Method = "GET"; Url = "$BackendUrl/admin/user/07251713305620000" }
)

foreach ($ep in $userEndpoints) {
    $result = Test-Endpoint -Name $ep.Name -Method $ep.Method -Url $ep.Url -Headers $headers
    if ($result.Success) { $passed++ } else { $failed++ }
}

# Role endpoints
Write-Host "`n=== 4. Role Management API Tests ===" -ForegroundColor Cyan
$roleEndpoints = @(
    @{ Name = "Get Role List"; Method = "GET"; Url = "$BackendUrl/admin/role/list" },
    @{ Name = "Get Role Menus"; Method = "GET"; Url = "$BackendUrl/admin/role/menus" },
    @{ Name = "Get Role Permissions"; Method = "GET"; Url = "$BackendUrl/admin/role/permissions" }
)

foreach ($ep in $roleEndpoints) {
    $result = Test-Endpoint -Name $ep.Name -Method $ep.Method -Url $ep.Url -Headers $headers
    if ($result.Success) { $passed++ } else { $failed++ }
}

# Menu endpoints
Write-Host "`n=== 5. Menu Management API Tests ===" -ForegroundColor Cyan
$menuEndpoints = @(
    @{ Name = "Get Menu Tree"; Method = "GET"; Url = "$BackendUrl/admin/menu/tree" },
    @{ Name = "Get Menu List"; Method = "GET"; Url = "$BackendUrl/admin/menu/list" }
)

foreach ($ep in $menuEndpoints) {
    $result = Test-Endpoint -Name $ep.Name -Method $ep.Method -Url $ep.Url -Headers $headers
    if ($result.Success) { $passed++ } else { $failed++ }
}

# 5. Test Frontend Static Resources
Write-Host "`n=== 6. Frontend Static Resource Tests ===" -ForegroundColor Cyan
$frontendEndpoints = @(
    @{ Name = "Frontend Index"; Method = "GET"; Url = "$FrontendUrl/" },
    @{ Name = "Frontend Assets"; Method = "GET"; Url = "$FrontendUrl/src/main.js" }
)

foreach ($ep in $frontendEndpoints) {
    $result = Test-Endpoint -Name $ep.Name -Method $ep.Method -Url $ep.Url
    if ($result.Success) { $passed++ } else { $failed++ }
}

# 6. Test Cross-Origin (CORS)
Write-Host "`n=== 7. CORS Configuration Test ===" -ForegroundColor Cyan
try {
    $corsResult = Invoke-WebRequest -Uri "$BackendUrl/actuator/health" -Method OPTIONS -Headers @{Origin = $FrontendUrl} -TimeoutSec 5
    # Check for either Access-Control-Allow-Origin or the CORS header
    if ($corsResult.Headers["Access-Control-Allow-Origin"] -or $corsResult.Headers["Access-control-Allow-Origin"]) {
        Write-Host "  [PASS] CORS is configured" -ForegroundColor Green
        $passed++
    } else {
        Write-Host "  [FAIL] CORS headers not found" -ForegroundColor Red
        $failed++
    }
}
catch {
    Write-Host "  [FAIL] CORS test failed: $($_.Exception.Message)" -ForegroundColor Red
    $failed++
}

# Summary
Write-Host "`n========================================" -ForegroundColor Magenta
Write-Host "Integration Test Summary" -ForegroundColor Magenta
Write-Host "========================================" -ForegroundColor Magenta
Write-Host "Passed:  $passed" -ForegroundColor Green
Write-Host "Failed:  $failed" -ForegroundColor Red
Write-Host "Skipped: $skipped" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Magenta

if ($failed -eq 0) {
    Write-Host "`nAll tests passed! Frontend and Backend are working together." -ForegroundColor Green
} else {
    Write-Host "`nSome tests failed. Please check the errors above." -ForegroundColor Red
}

exit $failed
