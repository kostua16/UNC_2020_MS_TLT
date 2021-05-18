#!/usr/bin/env sh
java org.springframework.boot.loader.JarLauncher -Xms${APP_XMS} -Xmx${APP_XMX} -Xss512K -XX:MaxMetaspaceSize=140m -XX:MaxDirectMemorySize=50m -Djava.security.egd=file:/dev/./urandom -Dserver.port=${PORT}
