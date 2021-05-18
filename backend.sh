#!/usr/bin/env sh
java org.springframework.boot.loader.JarLauncher -Xms${APP_XMS} -Xmx${APP_XMX} -Xss1M -Djava.security.egd=file:/dev/./urandom -Dserver.port=${PORT}
