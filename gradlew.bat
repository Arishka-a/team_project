@echo off
rem Gradle Wrapper for Windows
rem Auto-generated for your project

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_HOME=%DIRNAME%

set GRADLE_WRAPPER_JAR=%APP_HOME%gradle\wrapper\gradle-wrapper.jar
set GRADLE_WRAPPER_PROPS=%APP_HOME%gradle\wrapper\gradle-wrapper.properties

if not exist "%GRADLE_WRAPPER_JAR%" (
    echo.
    echo ERROR: Gradle Wrapper JAR not found!
    echo Run: gradlew --version first to download it.
    pause
    exit /b 1
)

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

if defined JAVA_HOME (
    set JAVA_EXE=%JAVA_HOME%\bin\java.exe
) else (
    echo.
    echo ERROR: JAVA_HOME is not set and no 'java' command found.
    echo Install JDK 17 and set JAVA_HOME.
    pause
    exit /b 1
)

:execute
"%JAVA_EXE%" -cp "%GRADLE_WRAPPER_JAR%" org.gradle.wrapper.GradleWrapperMain %*