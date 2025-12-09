@echo off
echo Cleaning bin directory...
del /q bin\*.class 2>nul

echo Compiling with Java 11...
"C:\Program Files\Eclipse Adoptium\jdk-11.0.28.6-hotspot\bin\javac.exe" -cp "lib\student.jar;src" -d bin src\*.java

if %errorlevel% equ 0 (
    echo Compilation successful!
    echo Verifying class file version...
    "C:\Program Files\Eclipse Adoptium\jdk-11.0.28.6-hotspot\bin\javap.exe" -verbose bin\AirControlTest.class | findstr "major"
) else (
    echo Compilation failed!
)
