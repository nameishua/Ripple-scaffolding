# Ripple 前端构建脚本
# 用于构建Vue项目

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Ripple Frontend Build Script" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$FrontendDir = "d:\Codex\Ripple-scaffolding\vue-admin"

Write-Host "Step 1: Installing dependencies..." -ForegroundColor Yellow
cd $FrontendDir
npm install

if ($LASTEXITCODE -ne 0) {
    Write-Host "npm install failed!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Step 2: Building production files..." -ForegroundColor Yellow
npm run build

if ($LASTEXITCODE -ne 0) {
    Write-Host "Build failed!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "Build completed successfully!" -ForegroundColor Green
Write-Host "Production files location: $FrontendDir\dist" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
