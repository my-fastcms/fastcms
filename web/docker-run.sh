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

# 获取镜像ID
imageId=`sudo docker images | grep ${PROJECT_NAME} | grep ${version}  | awk '{print $3}'`
echo "find ${PROJECT_NAME} imageId ${imageId}"

# 查看所有容器
docker ps -a

# 获取容器ID
containerId=`sudo docker ps -a  | grep $PROJECT_NAME | awk '{print $1}'`
echo "${PROJECT_NAME} contaniner id is ${containerId}"

# 停止并删除容器
echo "\nsudo docker stop ${containerId};sudo docker container rm ${containerId}"
docker stop $containerId
docker container rm $containerId

echo "exec : docker run wangjun/fastcms:"${version}
docker run -d -p 8080:8080 --network=host -e JAVA_OPTS="${JAVA_OPTS}" -e SPRING_PROFILES_ACTIVE=prod --name $CONTAINER_NAME $IMAGE_NAME
