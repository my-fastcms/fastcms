@echo off

REM windows package

REM package
call mvn clean install -Dmaven.test.skip=true

REM del fastcms-dist
rmdir .dist /s /q

REM create fastcms-dist
mkdir .dist
mkdir .dist\config
mkdir .dist\plugins
mkdir .dist\upload
mkdir .dist\htmls
mkdir .dist\logs

REM copy main program
xcopy web\target\fastcms-web-*-exec.jar .dist /s /i
xcopy web\src\main\resources\application-prod.yml .dist\config /s
REM xcopy doc\sql\* .dist\config /s

REM copy htmls
xcopy templates\src\main\resources .dist\htmls /s /i

xcopy web\startup.cmd .dist /s
xcopy web\startup.sh .dist /s
xcopy web\shutdown.cmd .dist /s
xcopy web\shutdown.sh .dist /s
xcopy web\Dockerfile .dist /s
xcopy web\docker-build.sh .dist /s
xcopy web\docker-run.sh .dist /s

cd .dist

REM run main
rename fastcms-web-*-exec.jar fastcms-server.jar

