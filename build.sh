#!/bin/sh

mvn clean install -Dmaven.test.skip=true

# del fastcms-dist
rm -rf dist

# create fastcms-dist
mkdir dist
mkdir dist/config
mkdir dist/plugins
mkdir dist/upload
mkdir dist/htmls
mkdir dist/logs

# copy main program and config
cp web/target/fastcms-web-*-exec.jar dist
cp web/src/main/resources/application-prod.yml dist/config
# cp doc/sql/* dist/config

# copy htmls
cp -r templates/src/main/resources dist

cp web/startup.cmd dist
cp web/startup.sh dist
cp web/shutdown.cmd dist
cp web/shutdown.sh dist
cp web/Dockerfile dist
cp web/docker-build.sh dist
cp web/docker-run.sh dist

cd dist

# run main
mv fastcms-web-*-exec.jar fastcms-server.jar
