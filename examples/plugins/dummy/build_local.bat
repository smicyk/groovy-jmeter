@echo off

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.

call %DIRNAME%/gradlew.bat clean build publishIvyPublicationToIvyRepository