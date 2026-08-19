@echo off
set SERVER_PORT=40697
call gradlew.bat bootJar -q
if errorlevel 1 exit /b 1
for %%f in (build\libs\*.jar) do set JAR_FILE=%%f
java -jar "%JAR_FILE%" --server.port=40697
