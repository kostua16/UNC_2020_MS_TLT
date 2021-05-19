#!/usr/bin/env sh

JAVA_TOOL_OPTIONS="-XX:+UnlockExperimentalVMOptions -XX:+UseSerialGC -XX:+UseCGroupMemoryLimitForHeap -XX:+UseCompressedOops -XX:+UseCompressedClassPointers -XX:MinHeapFreeRatio=20 XX:MaxHeapFreeRatio=40 -Xms${APP_XMX} -Xmx${APP_XMX} -Xss256K -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=64m -XX:MaxDirectMemorySize=36m -XX:MaxRAM=64m -XX:MaxPermSize=64M -server -Djava.util.concurrent.ForkJoinPool.common.parallelism=4 -XX:+TieredCompilation -XX:TieredStopAtLevel=1"
JAVA_OPTS=${JAVA_TOOL_OPTIONS}
echo "------- [APPLICATION STARTED] -------"
java org.springframework.boot.loader.JarLauncher  -Djava.security.egd=file:/dev/./urandom -Dserver.port=${PORT} ${JAVA_OPTS}
echo "------- [APPLICATION FINISHED] -------"
