FROM openjdk:8-jdk-alpine AS build
WORKDIR /home/app
ARG JAR_FILE
COPY $JAR_FILE /home/app/app.jar
RUN java -Djarmode=layertools -jar /home/app/app.jar extract

FROM openjdk:8-jdk-alpine AS package
WORKDIR /home/app/
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser
COPY --from=build /home/app/dependencies/ ./
COPY --from=build /home/app/spring-boot-loader/ ./
COPY --from=build /home/app/snapshot-dependencies/ ./
COPY --from=build /home/app/application/ ./
ARG XMS=100m
ARG XMX=200m
ENV PORT=8080
ENV APP_XMS=${XMS}
ENV APP_XMX=${XMX}
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher", " -Xms:${APP_XMS}", "-Xmx:${APP_XMX}", "-Xss:1m"]
