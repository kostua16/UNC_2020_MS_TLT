#!/usr/bin/env sh
JAVA_TOOL_OPTIONS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Xms${APP_XMS} -Xmx${APP_XMX} -Xss512K -XX:MetaspaceSize=100m -XX:MaxMetaspaceSize=100m -XX:MaxDirectMemorySize=50m"
java org.springframework.boot.loader.JarLauncher  -Djava.security.egd=file:/dev/./urandom -Dserver.port=${PORT} ${JAVA_TOOL_OPTIONS}
