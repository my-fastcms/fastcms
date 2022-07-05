FROM adoptopenjdk/openjdk8
LABEL maintainer="WangJun<wjun_java@163.com>"

WORKDIR /opt/fastcms

COPY . /opt/fastcms

RUN chmod +x /opt/fastcms/startup.sh &&  \
    rm -rf /opt/fastcms/startup.cmd && \
    rm -rf /opt/fastcms/shutdown.cmd && \
    rm -rf /opt/fastcms/Dockerfile && \
    rm -rf /opt/fastcms/docker-build.sh && \
    rm -rf /opt/fastcms/docker-run.sh

VOLUME ["/opt/fastcms/config", "/opt/fastcms/htmls", "/opt/fastcms/logs", "/opt/fastcms/upload", "/opt/fastcms/plugins", "/opt/fastcms/lucene"]

EXPOSE 8080

ENTRYPOINT ["nohup", "java", "-jar", "/opt/fastcms/fastcms-server.jar", "&"]
