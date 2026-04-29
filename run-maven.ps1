# run-maven.ps1 — Maven launcher that forces Java 21 (overrides JDK 26 system default)
# Usage: .\run-maven.ps1 [maven-arguments...]
# Examples:
#   .\run-maven.ps1 clean install -DskipTests
#   .\run-maven.ps1 spring-boot:run

$ErrorActionPreference = "Stop"

# ── Configuration ──────────────────────────────────────────────────────────────
$JAVA21_HOME = "C:\Program Files\Java\jdk-21"
$BACKEND_DIR = Split-Path -Parent $MyInvocation.MyCommand.Path
$WRAPPER_JAR = Join-Path $BACKEND_DIR ".mvn\wrapper\maven-wrapper.jar"

# ── Validate Java 21 ───────────────────────────────────────────────────────────
$javaExe = Join-Path $JAVA21_HOME "bin\java.exe"
if (-not (Test-Path $javaExe)) {
    Write-Error "JDK 21 not found at: $JAVA21_HOME"
    Write-Error "Please install JDK 21 LTS."
    exit 1
}

# ── Download maven-wrapper.jar if missing ─────────────────────────────────────
if (-not (Test-Path $WRAPPER_JAR)) {
    Write-Host "Downloading maven-wrapper.jar..." -ForegroundColor Cyan
    $wrapperUrl = "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"
    [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
    (New-Object Net.WebClient).DownloadFile($wrapperUrl, $WRAPPER_JAR)
    Write-Host "Downloaded." -ForegroundColor Green
}

# ── Override JAVA_HOME for child processes ────────────────────────────────────
$env:JAVA_HOME = $JAVA21_HOME

$jvmArgs = @(
    "-Xmx768m",
    "-classpath", $WRAPPER_JAR,
    "-Dmaven.multiModuleProjectDirectory=$BACKEND_DIR",
    "org.apache.maven.wrapper.MavenWrapperMain"
) + $args

Write-Host "Using Java: $javaExe" -ForegroundColor Cyan
Write-Host "Running: mvn $($args -join ' ')" -ForegroundColor Cyan
Write-Host ""

& $javaExe @jvmArgs
exit $LASTEXITCODE
