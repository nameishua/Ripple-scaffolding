# Ripple 后端启动脚本（后台运行）
# 使用nohup方式在后台持续运行

param(
    [ValidateSet("dev", "local", "prod")]
    [string]$Profile = "dev"
)

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Ripple Backend Start Script (Background)" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Profile: $Profile" -ForegroundColor Yellow
Write-Host "Log file: backend.log" -ForegroundColor Yellow
Write-Host ""

$ProjectRoot = $PSScriptRoot

cd $ProjectRoot

Write-Host "Starting Spring Boot application in background..." -ForegroundColor Green

$env:SPRING_PROFILES_ACTIVE = $Profile

Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$ProjectRoot'; `$env:SPRING_PROFILES_ACTIVE='$Profile'; mvn spring-boot:run -DskipTests 2>&1 | Tee-Object -FilePath 'backend.log'" -WindowStyle Hidden

Start-Sleep -Seconds 5

if (Test-Path "backend.log") {
    Write-Host ""
    Write-Host "Backend is starting... Check backend.log for details." -ForegroundColor Green
    Write-Host "You can view logs with: Get-Content backend.log -Wait -Tail 50" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Server will be available at: http://localhost:8883/ripple" -ForegroundColor Green
} else {
    Write-Host "Failed to start backend!" -ForegroundColor Red
}
