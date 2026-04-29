@ECHO OFF
TITLE CareerConnect - Start Backend
COLOR 0A
ECHO.
ECHO ========================================
ECHO   CareerConnect Backend Startup
ECHO ========================================
ECHO.

REM Check if MySQL is running
sc query MySQL80 | find "RUNNING" > nul 2>&1
IF ERRORLEVEL 1 (
  ECHO [!] MySQL80 service is NOT running.
  ECHO [*] Attempting to start MySQL80...
  net start MySQL80
  IF ERRORLEVEL 1 (
    ECHO.
    ECHO [ERROR] Could not start MySQL80 automatically.
    ECHO         Please open Services (services.msc) and start MySQL80 manually.
    ECHO         Then re-run this script.
    PAUSE
    EXIT /B 1
  )
  ECHO [OK] MySQL80 started.
) ELSE (
  ECHO [OK] MySQL80 is already running.
)

ECHO.
ECHO [*] Starting Spring Boot backend (Java 21)...
ECHO [*] Backend will be available at: http://localhost:8080
ECHO [*] Press Ctrl+C to stop.
ECHO.

PowerShell -NoProfile -ExecutionPolicy Bypass -File "%~dp0run-maven.ps1" spring-boot:run

PAUSE
