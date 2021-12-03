REM windows package

REM package
call mvn clean install -Dmaven.test.skip=true

REM del fastcms-dist
rmdir .dist /s /q

REM create fastcms-dist
mkdir .dist
mkdir .dist\plugins
mkdir .dist\upload
mkdir .dist\htmls

REM copy main program
xcopy web\target\fastcms-web-*-exec.jar .dist /s /i
xcopy web\src\main\resources\application-prod.yml .dist /s

REM copy htmls
xcopy web\src\main\resources\htmls .dist\htmls /s /i

xcopy web\start.bat .dist /s
xcopy web\start.sh .dist /s

cd .dist

REM run main
rename fastcms-web-*-exec.jar fastcms-start.jar
rename application-prod.yml application.yml

