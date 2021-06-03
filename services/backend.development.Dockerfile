FROM openjdk:8-jdk-alpine AS package
ENV PORT=8080
ENV APP_XMS=100M
ENV APP_XMX=300M
ENTRYPOINT java -jar /home/app/app.jar -Xms${APP_XMS} -Xmx${APP_XMX} -Xss1M -Dspring.devtools.restart.enabled=true -Djava.security.egd=file:/dev/./urandom -Dserver.port=${PORT}
EXPOSE ${PORT}
WORKDIR /home/app/
ARG JAR_FILE
COPY $JAR_FILE /home/app/app.jar
