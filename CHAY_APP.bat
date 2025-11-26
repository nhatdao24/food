@echo off
echo ========================================
echo KHOI DONG APP - FOOD APP
echo ========================================
echo.
echo Dang build va cai dat app...
echo.

cd /d "%~dp0"

echo [1/3] Clean project...
call gradlew.bat clean

echo.
echo [2/3] Build debug APK...
call gradlew.bat assembleDebug

echo.
echo [3/3] Cai dat vao thiet bi...
call gradlew.bat installDebug

echo.
echo ========================================
echo HOAN THANH!
echo ========================================
echo App da duoc cai dat vao thiet bi/emulator
echo Hay mo app "Food App" tren thiet bi
echo.
pause

