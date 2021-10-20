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
xcopy fastcms-web\target\fastcms-web-*-exec.jar .dist /s /i
xcopy fastcms-web\src\main\resources\application-prod.properties .dist /s

REM copy plugins
xcopy plugins\fastcms-cms-plugin\target\*.jar .dist\plugins /s
xcopy plugins\fastcms-mall-plugin\target\*.jar .dist\plugins /s

REM copy htmls
xcopy fastcms-web\src\main\resources\htmls .dist\htmls /s /i

xcopy fastcms-web\start.bat .dist /s
xcopy fastcms-web\start.sh .dist /s

cd .dist

REM run main
rename fastcms-web-*-exec.jar fastcms-start.jar

