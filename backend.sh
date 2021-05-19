#!/usr/bin/env bash
if [[ -z "${APP_XMX}" ]]; then
    export APP_XMX=248M
fi
if [[ -z "${APP_XMS}" ]]; then
    export APP_XMS=100M
fi
JAVA_TOOL_OPTIONS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:+UseCompressedOops -XX:+UseCompressedClassPointer -XX:+UnlockDiagnosticVMOptions -Xms${APP_XMX} -Xmx${APP_XMX} -Xss512K -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=64m -XX:MaxDirectMemorySize=36m"
java -XX:+PrintFlagsFinal
java org.springframework.boot.loader.JarLauncher  -Djava.security.egd=file:/dev/./urandom -Dserver.port=${PORT} ${JAVA_TOOL_OPTIONS}
