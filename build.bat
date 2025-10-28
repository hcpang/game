@echo off
REM Build script for the game project
REM This script compiles all Java source files using the Eclipse-bundled JDK

echo Building game project...

REM Set the Java compiler path (Eclipse-bundled JDK 22)
set JAVAC=C:\Users\yharm\.p2\pool\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_22.0.2.v20240802-1626\jre\bin\javac.exe

REM Check if javac exists
if not exist "%JAVAC%" (
    echo ERROR: Java compiler not found at %JAVAC%
    echo Please update the JAVAC path in this script or use Eclipse to build.
    exit /b 1
)

REM Compile main source files (excluding tests which require JUnit)
"%JAVAC%" -cp "lib\AlphaBetaLib_v0.2.jar" -d bin src\common\*.java src\mancala\*.java src\mancala\evaluation\*.java src\ultimatetictactoe\*.java src\ultimatetictactoe\evaluation\*.java src\ultimatetictactoe\evaluation\weights\*.java

if %ERRORLEVEL% EQU 0 (
    echo Build successful!
    echo Compiled classes are in the bin\ directory
) else (
    echo Build failed with error code %ERRORLEVEL%
    exit /b %ERRORLEVEL%
)
