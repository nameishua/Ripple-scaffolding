# Ripple 开发环境一键启动（后端 + 前端，后台运行）
#
# 用法:
#   .\start-dev.ps1
#   .\start-dev.ps1 -Profile local
#   .\start-dev.ps1 -Profile dev -Port 3000

param(
    [ValidateSet("dev", "local", "prod")]
    [string]$Profile = "local",
    [int]$Port = 3000
)

$ProjectRoot = $PSScriptRoot
$FrontendDir = Join-Path $ProjectRoot "vue-admin"
$BackendLog = Join-Path $ProjectRoot "backend.log"
$FrontendLog = Join-Path $ProjectRoot "frontend.log"

function Write-Info($Text, $Color = "Gray") {
    Write-Host $Text -ForegroundColor $Color
}

Write-Info "========================================" "Cyan"
Write-Info "Ripple Dev Environment" "Cyan"
Write-Info "========================================" "Cyan"
Write-Info ""
Write-Info "Profile : $Profile" "Yellow"
Write-Info "Backend : http://localhost:8883/ripple" "Yellow"
Write-Info "Frontend: http://localhost:$Port" "Yellow"
Write-Info ""

# --- Backend ---
Write-Info "[1/2] Starting backend..." "Green"
$env:SPRING_PROFILES_ACTIVE = $Profile
Start-Process powershell -ArgumentList @(
    "-NoExit",
    "-Command",
    "cd '$ProjectRoot'; `$env:SPRING_PROFILES_ACTIVE='$Profile'; mvn spring-boot:run -DskipTests 2>&1 | Tee-Object -FilePath '$BackendLog'"
) -WindowStyle Hidden

# --- Frontend ---
Write-Info "[2/2] Starting frontend..." "Green"
Start-Process powershell -ArgumentList @(
    "-NoExit",
    "-Command",
    "cd '$FrontendDir'; npm run dev 2>&1 | Tee-Object -FilePath '$FrontendLog'"
) -WindowStyle Hidden

Start-Sleep -Seconds 6

Write-Info ""
if ((Test-Path $BackendLog) -or (Test-Path $FrontendLog)) {
    Write-Info "Services are starting in background." "Green"
    Write-Info ""
    Write-Info "Logs:" "Cyan"
    if (Test-Path $BackendLog) {
        Write-Info "  Backend : Get-Content '$BackendLog' -Wait -Tail 50"
    }
    if (Test-Path $FrontendLog) {
        Write-Info "  Frontend: Get-Content '$FrontendLog' -Wait -Tail 30"
    }
    Write-Info ""
    Write-Info "Manage: .\manage-dev.ps1 status | stop | restart | logs" "Cyan"
} else {
    Write-Info "Startup may have failed. Check that Java, Maven and Node.js are installed." "Red"
}
