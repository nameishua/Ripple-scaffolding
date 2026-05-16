# Ripple 后端运行脚本
# 使用nohup方式在后台持续运行Spring Boot应用

param(
    [ValidateSet("dev", "local", "prod")]
    [string]$Profile = "local"
)

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Ripple Backend Run Script (Background)" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Starting with profile: $Profile" -ForegroundColor Yellow
Write-Host "Server URL: http://localhost:8883/ripple" -ForegroundColor Yellow
Write-Host ""

$ProjectRoot = "d:\Codex\Ripple-scaffolding"

Write-Host "Starting Spring Boot application in background (nohup)..." -ForegroundColor Green
Write-Host ""

cd $ProjectRoot
$env:SPRING_PROFILES_ACTIVE = $Profile

Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$ProjectRoot'; `$env:SPRING_PROFILES_ACTIVE='$Profile'; mvn spring-boot:run -DskipTests 2>&1 | Tee-Object -FilePath 'backend.log'" -WindowStyle Hidden

Start-Sleep -Seconds 5

if (Test-Path "backend.log") {
    Write-Host ""
    Write-Host "Backend is starting in background..." -ForegroundColor Green
    Write-Host "Log file: $ProjectRoot\backend.log" -ForegroundColor Cyan
    Write-Host "Check logs: Get-Content backend.log -Wait -Tail 50" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Server will be available at: http://localhost:8883/ripple" -ForegroundColor Green
} else {
    Write-Host "Failed to start backend!" -ForegroundColor Red
}
