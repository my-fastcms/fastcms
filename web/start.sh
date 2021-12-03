# Java 命令行参数
JAVA_OPTS="-XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -Xms256m -Xmx256m -Xmn64m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC"
java -jar -Djava.awt.headless=true -Xverify:none ${JAVA_OPTS} ./fastcms-start.jar --spring.profiles.active=prod &