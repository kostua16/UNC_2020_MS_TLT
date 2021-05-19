#!/usr/bin/env bash
if [[ -z "${APP_XMX}" ]]; then
    export APP_XMX=248M
fi
if [[ -z "${APP_XMS}" ]]; then
    export APP_XMS=100M
fi
echo "------- [DEFAULT JAVA OPTIONS] -------"
java -XX:+PrintFlagsFinal
echo "------- [CONFIGURED JAVA OPTIONS] -------"
JAVA_TOOL_OPTIONS="-XX:+UnlockExperimentalVMOptions -XX:+UseSerialGC -XX:+UseCGroupMemoryLimitForHeap -XX:+UseCompressedOops -XX:+UseCompressedClassPointer -XX:+UnlockDiagnosticVMOptions -Xms${APP_XMX} -Xmx${APP_XMX} -Xss512K -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=64m -XX:MaxDirectMemorySize=36m -XX:MaxRAM=72m"
java -XX:+PrintFlagsFinal ${JAVA_TOOL_OPTIONS}
echo "------- [APPLICATION STARTED] -------"
java org.springframework.boot.loader.JarLauncher  -Djava.security.egd=file:/dev/./urandom -Dserver.port=${PORT} ${JAVA_TOOL_OPTIONS}
echo "------- [APPLICATION FINISHED] -------"
