# 初始化 Ripple MySQL 库（建表 + 演示数据）
param(
    [string]$MysqlBin = "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe",
    [string]$DbHost = "127.0.0.1",
    [string]$User = "root",
    [string]$Password = "root",
    [string]$Database = "ripple"
)

$Root = Split-Path $PSScriptRoot -Parent
$Schema = Join-Path $Root "src\main\resources\schema-mysql.sql"
$Seed = Join-Path $Root "src\main\resources\seed-data-mysql.sql"

if (-not (Test-Path $MysqlBin)) {
    Write-Host "mysql.exe not found: $MysqlBin" -ForegroundColor Red
    exit 1
}

Write-Host "Creating database [$Database]..." -ForegroundColor Cyan
& $MysqlBin -h $DbHost -u $User "-p$Password" -e "CREATE DATABASE IF NOT EXISTS $Database DEFAULT CHARSET utf8mb4;"

Write-Host "Applying schema..." -ForegroundColor Cyan
Get-Content $Schema -Encoding UTF8 -Raw | & $MysqlBin -h $DbHost -u $User "-p$Password" --default-character-set=utf8mb4 $Database

Write-Host "Applying seed data..." -ForegroundColor Cyan
Get-Content $Seed -Encoding UTF8 -Raw | & $MysqlBin -h $DbHost -u $User "-p$Password" --default-character-set=utf8mb4 $Database

Write-Host "Done." -ForegroundColor Green
& $MysqlBin -h $DbHost -u $User "-p$Password" --default-character-set=utf8mb4 $Database -e "SELECT COUNT(*) AS roles FROM rp_role; SELECT COUNT(*) AS menus FROM rp_menu;"
