# Ripple Frontend Management Script
# Supports: start, stop, restart, logs, status

param(
    [ValidateSet("start", "stop", "restart", "logs", "status")]
    [string]$Command = "status",
    [int]$Port = 3000
)

$ProjectRoot = Split-Path $PSScriptRoot -Parent
$FrontendRoot = Join-Path $ProjectRoot "vue-admin"
$LogFile = "$ProjectRoot\frontend.log"

function Write-ColorOutput($Text, $Color = "Gray") {
    Write-Host $Text -ForegroundColor $Color
}

function Get-FrontendProcess {
    return Get-Process | Where-Object {$_.CommandLine -like "*vite*" -or $_.CommandLine -like "*ripple*vue*" -or $_.ProcessName -eq "node"}
}

function Start-Frontend {
    Write-ColorOutput "========================================" "Cyan"
    Write-ColorOutput "Starting Ripple Frontend" "Cyan"
    Write-ColorOutput "========================================" "Cyan"

    $process = Get-FrontendProcess
    if ($process) {
        Write-ColorOutput "Frontend already running!" "Yellow"
        return
    }

    Write-ColorOutput "Starting frontend service..." "Green"

    Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$FrontendRoot'; npm run dev 2>&1 | Tee-Object -FilePath '$LogFile'" -WindowStyle Hidden

    Start-Sleep -Seconds 5

    if (Test-Path $LogFile) {
        Write-ColorOutput "OK Frontend started!" "Green"
        Write-ColorOutput "URL: http://localhost:$Port" "Yellow"
        Write-ColorOutput "Log: Get-Content $LogFile -Tail 50 -Wait" "Gray"
    } else {
        Write-ColorOutput "Failed to start!" "Red"
    }
}

function Stop-Frontend {
    Write-ColorOutput "========================================" "Cyan"
    Write-ColorOutput "Stopping Ripple Frontend" "Cyan"
    Write-ColorOutput "========================================" "Cyan"

    $process = Get-FrontendProcess
    if (!$process) {
        Write-ColorOutput "Frontend not running!" "Yellow"
        return
    }

    Write-ColorOutput "Stopping frontend service..." "Yellow"

    $process | Stop-Process -Force
    Start-Sleep -Seconds 2

    $process = Get-FrontendProcess
    if (!$process) {
        Write-ColorOutput "OK Frontend stopped!" "Green"
    } else {
        Write-ColorOutput "Failed to stop!" "Red"
    }
}

function Show-FrontendStatus {
    Write-ColorOutput "========================================" "Cyan"
    Write-ColorOutput "Ripple Frontend Status" "Cyan"
    Write-ColorOutput "========================================" "Cyan"

    $process = Get-FrontendProcess
    if ($process) {
        Write-ColorOutput "OK Status: Running" "Green"
        Write-ColorOutput "Process ID: $($process.Id)" "Gray"
        Write-ColorOutput "Memory: $([math]::Round($process.WorkingSet64 / 1MB, 2)) MB" "Gray"
        Write-ColorOutput "URL: http://localhost:$Port" "Yellow"
    } else {
        Write-ColorOutput "Status: Not running" "Red"
    }

    if (Test-Path $LogFile) {
        $size = (Get-Item $LogFile).Length
        Write-ColorOutput "Log: $LogFile ($([math]::Round($size / 1KB, 2)) KB)" "Gray"
    }
}

function Show-FrontendLogs {
    Write-ColorOutput "========================================" "Cyan"
    Write-ColorOutput "Ripple Frontend Logs" "Cyan"
    Write-ColorOutput "========================================" "Cyan"
    Write-ColorOutput "Press Ctrl+C to exit" "Yellow"
    Write-ColorOutput ""

    if (Test-Path $LogFile) {
        Get-Content $LogFile -Tail 50 -Wait
    } else {
        Write-ColorOutput "Log file not found!" "Red"
    }
}

switch ($Command) {
    "start" { Start-Frontend }
    "stop" { Stop-Frontend }
    "restart" { Stop-Frontend; Start-Sleep -Seconds 2; Start-Frontend }
    "logs" { Show-FrontendLogs }
    "status" { Show-FrontendStatus }
    default { Show-FrontendStatus }
}

Write-ColorOutput ""
