# Ripple 前端运行脚本
# 使用nohup方式在后台持续运行Vue开发服务器

param(
    [int]$Port = 3000
)

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Ripple Frontend Run Script (Background)" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Starting development server in background..." -ForegroundColor Yellow
Write-Host "Frontend URL: http://localhost:$Port" -ForegroundColor Yellow
Write-Host ""

$FrontendDir = "d:\Codex\Ripple-scaffolding\vue-admin"

Write-Host "Starting Vite dev server in background (nohup)..." -ForegroundColor Green
Write-Host ""

Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$FrontendDir'; npm run dev 2>&1 | Tee-Object -FilePath '..\frontend.log'" -WindowStyle Hidden

Start-Sleep -Seconds 5

if (Test-Path "frontend.log") {
    Write-Host ""
    Write-Host "Frontend is starting in background..." -ForegroundColor Green
    Write-Host "Log file: $FrontendDir\..\frontend.log" -ForegroundColor Cyan
    Write-Host "Check logs: Get-Content ..\frontend.log -Wait -Tail 30" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Frontend will be available at: http://localhost:$Port" -ForegroundColor Green
} else {
    Write-Host "Failed to start frontend!" -ForegroundColor Red
}
