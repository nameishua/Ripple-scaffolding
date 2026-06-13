# Ripple 后端构建脚本
# 用于构建Spring Boot项目

param(
    [string]$Profile = "dev"
)

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Ripple Backend Build Script" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$ProjectRoot = Split-Path $PSScriptRoot -Parent

Write-Host "Step 1: Clean and compile project..." -ForegroundColor Yellow
cd $ProjectRoot
mvn clean compile

if ($LASTEXITCODE -ne 0) {
    Write-Host "Build failed!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Step 2: Package application..." -ForegroundColor Yellow
mvn package -DskipTests

if ($LASTEXITCODE -ne 0) {
    Write-Host "Package failed!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "Build completed successfully!" -ForegroundColor Green
Write-Host "JAR file location: $ProjectRoot\target\Ripple-1.2.2.jar" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
