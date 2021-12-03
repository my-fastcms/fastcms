#!/bin/sh

# linux、mac上打包的脚本
mvn clean install -Dmaven.test.skip=true

# del fastcms-dist
rm -rf dist

# create fastcms-dist
mkdir dist
mkdir dist/config
mkdir dist/plugins
mkdir dist/upload
mkdir dist/htmls

# copy main program and config
cp web/target/fastcms-web-*-exec.jar dist
cp web/src/main/resources/application-prod.yml dist/config

# copy htmls
cp -r web/src/main/resources/htmls dist

cp web/start.bat dist
cp web/start.sh dist

cd dist

# run main
mv fastcms-web-*-exec.jar fastcms-start.jar
