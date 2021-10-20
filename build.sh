#!/bin/sh

# linux、mac上打包的脚本
mvn clean install -Dmaven.test.skip=true

# del fastcms-dist
rm -rf dist

# create fastcms-dist
mkdir dist
mkdir dist/plugins
mkdir dist/upload
mkdir dist/htmls

# copy main program and config
cp fastcms-web/target/fastcms-web-*-exec.jar dist
cp fastcms-web/src/main/resources/application-prod.properties dist

# copy plugins
cp plugins/fastcms-cms-plugin/target/*.jar dist/plugins
cp plugins/fastcms-mall-plugin/target/*.jar dist/plugins

# copy htmls
cp -r fastcms-web/src/main/resources/htmls dist

cp fastcms-web/start.bat dist
cp fastcms-web/start.sh dist

cd dist

# run main
mv fastcms-web-*-exec.jar fastcms-start.jar
