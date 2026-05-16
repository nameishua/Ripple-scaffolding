# Ripple 开发环境统一管理（后端 + 前端）
#
# 用法:
#   .\manage-dev.ps1              # 查看状态
#   .\manage-dev.ps1 start
#   .\manage-dev.ps1 stop
#   .\manage-dev.ps1 restart
#   .\manage-dev.ps1 logs         # 后端日志（默认）
#   .\manage-dev.ps1 logs -Target frontend
#   .\manage-dev.ps1 logs -Target both

param(
    [ValidateSet("start", "stop", "restart", "logs", "status")]
    [string]$Command = "status",
    [ValidateSet("backend", "frontend", "both")]
    [string]$Target = "both",
    [ValidateSet("dev", "local", "prod")]
    [string]$Profile = "local",
    [int]$Port = 3000
)

$ProjectRoot = $PSScriptRoot

$backendScript = Join-Path $ProjectRoot "manage-backend.ps1"
$frontendScript = Join-Path $ProjectRoot "vue-admin\manage-frontend.ps1"

function Invoke-Backend($cmd) {
    if (-not (Test-Path $backendScript)) {
        Write-Host "Script not found: $backendScript" -ForegroundColor Red
        return
    }
    & $backendScript -Command $cmd -Profile $Profile
}

function Invoke-Frontend($cmd) {
    if (-not (Test-Path $frontendScript)) {
        Write-Host "Script not found: $frontendScript" -ForegroundColor Red
        return
    }
    & $frontendScript -Command $cmd -Port $Port
}

switch ($Command) {
    "start" {
        switch ($Target) {
            "backend"  { Invoke-Backend "start" }
            "frontend" { Invoke-Frontend "start" }
            default {
                Invoke-Backend "start"
                Start-Sleep -Seconds 2
                Invoke-Frontend "start"
            }
        }
    }
    "stop" {
        switch ($Target) {
            "backend"  { Invoke-Backend "stop" }
            "frontend" { Invoke-Frontend "stop" }
            default {
                Invoke-Frontend "stop"
                Start-Sleep -Seconds 1
                Invoke-Backend "stop"
            }
        }
    }
    "restart" {
        switch ($Target) {
            "backend"  { Invoke-Backend "restart" }
            "frontend" { Invoke-Frontend "restart" }
            default {
                Invoke-Backend "restart"
                Start-Sleep -Seconds 2
                Invoke-Frontend "restart"
            }
        }
    }
    "logs" {
        switch ($Target) {
            "backend"  { Invoke-Backend "logs" }
            "frontend" { Invoke-Frontend "logs" }
            "both" {
                Write-Host "=== Backend (Ctrl+C then frontend logs) ===" -ForegroundColor Cyan
                Invoke-Backend "logs"
            }
        }
    }
    default {
        Write-Host ""
        Invoke-Backend "status"
        Write-Host ""
        Invoke-Frontend "status"
    }
}
