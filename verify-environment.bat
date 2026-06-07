@echo off
echo ========================================
echo   DIAGNOSTIC OF JAVA ENVIRONMENT
echo ========================================
echo.

echo [1] Checking Java Runtime (java)...
java -version 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Java not found!
) else (
    echo [OK] Java installed
)
echo.

echo [2] Checking Java Compiler (javac)...
javac -version 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] JDK not found!
    echo.
    echo SOLUTION: Install the JDK from:
    echo https://www.oracle.com/java/technologies/downloads/
) else (
    echo [OK] JDK installed
)
echo.

echo [3] Checking libraries...
if exist "lib\gson-2.10.1.jar" (
    echo [OK] Gson found
) else (
    echo [ERROR] Gson NOT found in lib\
)

if exist "lib\sqlite-jdbc-3.42.0.0.jar" (
    echo [OK] SQLite JDBC found
) else (
    echo [ERROR] SQLite JDBC NOT found in lib\
)
echo.

echo [4] Checking source files...
if exist "src\Main.java" (
    echo [OK] Main.java found
) else (
    echo [ERROR] Main.java NOT found
)

if exist "src\model\Country.java" (
    echo [OK] Country.java found
) else (
    echo [ERROR] Country.java NOT found
)

if exist "src\dao\CountryDAO.java" (
    echo [OK] CountryDAO.java found
) else (
    echo [ERROR] CountryDAO.java NOT found
)

if exist "src\service\ApiService.java" (
    echo [OK] ApiService.java found
) else (
    echo [ERROR] ApiService.java NOT found
)

if exist "src\util\DatabaseUtil.java" (
    echo [OK] DatabaseUtil.java found
) else (
    echo [ERROR] DatabaseUtil.java NOT found
)

echo.
echo ========================================
echo   DIAGNOSTIC COMPLETED
echo ========================================
echo.
echo If any item shows [ERROR], fix it before compiling.
echo.
pause
