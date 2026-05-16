# Ripple Backend Management Script
# Supports: start, stop, restart, logs, status

param(
    [ValidateSet("start", "stop", "restart", "logs", "status")]
    [string]$Command = "status",
    [ValidateSet("dev", "local", "prod")]
    [string]$Profile = "local"
)

$ProjectRoot = $PSScriptRoot
$LogFile = "$ProjectRoot\backend.log"

function Write-ColorOutput($Text, $Color = "Gray") {
    Write-Host $Text -ForegroundColor $Color
}

function Get-BackendProcess {
    return Get-Process | Where-Object {$_.CommandLine -like "*Ripple*" -or ($_.ProcessName -eq "java" -and $_.MainWindowTitle -eq "")}
}

function Start-Backend {
    Write-ColorOutput "========================================" "Cyan"
    Write-ColorOutput "Starting Ripple Backend (Profile: $Profile)" "Cyan"
    Write-ColorOutput "========================================" "Cyan"

    $process = Get-BackendProcess
    if ($process) {
        Write-ColorOutput "Backend already running!" "Yellow"
        return
    }

    Write-ColorOutput "Starting backend service..." "Green"

    $env:SPRING_PROFILES_ACTIVE = $Profile

    Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$ProjectRoot'; `$env:SPRING_PROFILES_ACTIVE='$Profile'; mvn spring-boot:run -DskipTests 2>&1 | Tee-Object -FilePath '$LogFile'" -WindowStyle Hidden

    Start-Sleep -Seconds 5

    if (Test-Path $LogFile) {
        Write-ColorOutput "OK Backend started!" "Green"
        Write-ColorOutput "URL: http://localhost:8883/ripple" "Yellow"
        Write-ColorOutput "Log: Get-Content $LogFile -Tail 50 -Wait" "Gray"
    } else {
        Write-ColorOutput "Failed to start!" "Red"
    }
}

function Stop-Backend {
    Write-ColorOutput "========================================" "Cyan"
    Write-ColorOutput "Stopping Ripple Backend" "Cyan"
    Write-ColorOutput "========================================" "Cyan"

    $process = Get-BackendProcess
    if (!$process) {
        Write-ColorOutput "Backend not running!" "Yellow"
        return
    }

    Write-ColorOutput "Stopping backend service..." "Yellow"

    $process | Stop-Process -Force
    Start-Sleep -Seconds 2

    $process = Get-BackendProcess
    if (!$process) {
        Write-ColorOutput "OK Backend stopped!" "Green"
    } else {
        Write-ColorOutput "Failed to stop!" "Red"
    }
}

function Show-BackendStatus {
    Write-ColorOutput "========================================" "Cyan"
    Write-ColorOutput "Ripple Backend Status" "Cyan"
    Write-ColorOutput "========================================" "Cyan"

    $process = Get-BackendProcess
    if ($process) {
        Write-ColorOutput "OK Status: Running" "Green"
        Write-ColorOutput "Process ID: $($process.Id)" "Gray"
        Write-ColorOutput "Memory: $([math]::Round($process.WorkingSet64 / 1MB, 2)) MB" "Gray"
        Write-ColorOutput "URL: http://localhost:8883/ripple" "Yellow"
    } else {
        Write-ColorOutput "Status: Not running" "Red"
    }

    if (Test-Path $LogFile) {
        $size = (Get-Item $LogFile).Length
        Write-ColorOutput "Log: $LogFile ($([math]::Round($size / 1KB, 2)) KB)" "Gray"
    }
}

function Show-BackendLogs {
    Write-ColorOutput "========================================" "Cyan"
    Write-ColorOutput "Ripple Backend Logs" "Cyan"
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
    "start" { Start-Backend }
    "stop" { Stop-Backend }
    "restart" { Stop-Backend; Start-Sleep -Seconds 2; Start-Backend }
    "logs" { Show-BackendLogs }
    "status" { Show-BackendStatus }
    default { Show-BackendStatus }
}

Write-ColorOutput ""
