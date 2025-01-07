FROM openjdk:17
LABEL maintainer="WangJun<wjun_java@163.com>"

WORKDIR /opt/fastcms

ARG JAR_FILE=target/*-exec.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java $JAVA_OPTS -jar app.jar
