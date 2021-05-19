#!/usr/bin/env sh

JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseSerialGC -XX:+UseCGroupMemoryLimitForHeap -XX:+UseCompressedOops -XX:+UseCompressedClassPointers -XX:MinHeapFreeRatio=20 -XX:MaxHeapFreeRatio=40 -Xms${APP_XMX} -Xmx${APP_XMX} -Xss256K -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=64m -XX:MaxDirectMemorySize=36m -XX:MaxRAM=64m -XX:MaxPermSize=64M -server -XX:+TieredCompilation -XX:TieredStopAtLevel=1"
APP_ARGS="-Djava.util.concurrent.ForkJoinPool.common.parallelism=4 -Djava.security.egd=file:/dev/./urandom -Dserver.port=${PORT} -Dspring.profiles.active=${PROFILE} --spring.profiles.active=${PROFILE}"
echo "------- [ENV] -------"
env
echo "------- [APPLICATION STARTED] -------"
java ${JAVA_OPTS} org.springframework.boot.loader.JarLauncher  ${APP_ARGS}
echo "------- [APPLICATION FINISHED] -------"
