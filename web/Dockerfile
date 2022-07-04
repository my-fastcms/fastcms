FROM adoptopenjdk/openjdk8
LABEL maintainer="WangJun<wjun_java@163.com>"

WORKDIR /opt/fastcms

COPY .. /opt/fastcms

RUN chmod +x /opt/fastcms/startup.sh

VOLUME ["/opt/fastcms/config", "/opt/fastcms/htmls", "/opt/fastcms/logs", "/opt/fastcms/upload", "/opt/fastcms/plugins", "/opt/fastcms/lucene"]

EXPOSE 8080

CMD ["/opt/fastcms/startup.sh"]
