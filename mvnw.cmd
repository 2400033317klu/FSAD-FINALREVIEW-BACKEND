@ECHO OFF
SETLOCAL

REM ── Use available JDK 17 ──
SET "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.18.8-hotspot"

IF NOT EXIST "%JAVA_HOME%\bin\java.exe" (
  ECHO ERROR: JDK 17 not found at %JAVA_HOME%
  EXIT /B 1
)

REM ── Ensure maven-wrapper.jar is present ──
SET "WRAPPER_JAR=%~dp0.mvn\wrapper\maven-wrapper.jar"
IF NOT EXIST "%WRAPPER_JAR%" (
  ECHO Downloading maven-wrapper.jar ...
  PowerShell -NoProfile -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar', '%WRAPPER_JAR%')"
  IF NOT EXIST "%WRAPPER_JAR%" (
    ECHO FAILED to download maven-wrapper.jar. Check your internet connection.
    EXIT /B 1
  )
)

"%JAVA_HOME%\bin\java.exe" -Xmx512m ^
  -classpath "%WRAPPER_JAR%" ^
  "-Dmaven.multiModuleProjectDirectory=%~dp0" ^
  org.apache.maven.wrapper.MavenWrapperMain %*

SET "RESULT=%ERRORLEVEL%"
EXIT /B %RESULT%
