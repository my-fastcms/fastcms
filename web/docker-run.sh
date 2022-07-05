#!/bin/bash
# abort on errors
set -e

version="$1"

if [[ "$version" == "" ]]; then
	echo "./please designated docker image version"
	exit 0
fi

PROJECT_NAME=fastcms

IMAGE_NAME=wangjun/fastcms:${version}

#容器名
CONTAINER_NAME=$PROJECT_NAME-${version}

JAVA_OPTS="-XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Xms512m -Xmx512m -Xmn64m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC"

echo "exec : docker run wangjun/fastcms:"${version}
docker run -d -p 8080:8080 --network=host -e JAVA_OPTS="${JAVA_OPTS}" -e SPRING_PROFILES_ACTIVE=prod --name $CONTAINER_NAME $IMAGE_NAME
