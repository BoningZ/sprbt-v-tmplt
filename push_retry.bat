@echo off
setlocal

set max_attempts=32
set current_attempt=1

:retry
echo Attempt %current_attempt% of %max_attempts%
git push

if %errorlevel% neq 0 (
    if %current_attempt% equ %max_attempts% (
        echo Push failed after %max_attempts% attempts. Exiting.
        exit /b %errorlevel%
    )
    echo Push failed. Retrying in 5 seconds...
    ping 127.0.0.1 -n 6 > nul
    set /a current_attempt+=1
    goto retry
)

echo Push succeeded.
endlocal
