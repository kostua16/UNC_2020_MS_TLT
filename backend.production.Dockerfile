FROM kostua16/unc_2020_backend_base AS deps
WORKDIR /home/app
COPY pom.xml ./
COPY services/pom.xml ./services/
COPY services/config/pom.xml ./services/config/
COPY services/discovery/pom.xml ./services/discovery/
COPY services/proxy/pom.xml ./services/proxy/
COPY services/service_parent/pom.xml ./services/service_parent/
COPY services/account/pom.xml ./services/account/
COPY services/bank/pom.xml ./services/bank/
COPY services/common/pom.xml ./services/common/
COPY services/communal/pom.xml ./services/communal/
COPY services/gibdd/pom.xml ./services/gibdd/
COPY services/logging/pom.xml ./services/logging/
COPY services/passport/pom.xml ./services/passport/
COPY services/tax/pom.xml ./services/tax/
COPY services/repackage.skip ./services/
COPY services/common/repackage.skip ./services/common/
COPY services/common/src/ ./services/common/src/
RUN mvn install -pl Project2020,services,services/common,services/service_parent -T 1C -DskipTests=true -DskipInspections=true -DskipDocs=true

FROM deps AS build
ARG PROJECT
COPY services/${PROJECT}/src/ ./services/${PROJECT}/src/
RUN mvn install -am -pl services/${PROJECT} -T 1C -DskipTests=true -DskipInspections=true -DskipDocs=true && java -Djarmode=layertools -jar ./services/${PROJECT}/target/${PROJECT}-0.0.1-SNAPSHOT.jar extract

FROM openjdk:8-jdk-alpine AS package
ENV PORT=8080
ENV APP_XMS=100M
ENV APP_XMX=300M
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher", " -Xms${APP_XMS}", "-Xmx${APP_XMX}", "-Xss1m", "-Djava.security.egd=file:/dev/./urandom", "-Dserver.port=${PORT}"]
EXPOSE ${PORT}
RUN addgroup -S appgroup && adduser -S appuser -G appgroup && mkdir /home/app/ && chown appuser:appgroup /home/app
WORKDIR /home/app/
USER appuser
COPY --from=build --chown=appuser:appgroup /home/app/dependencies/ ./
COPY --from=build --chown=appuser:appgroup /home/app/spring-boot-loader/ ./
COPY --from=build --chown=appuser:appgroup /home/app/snapshot-dependencies/ ./
COPY --from=build --chown=appuser:appgroup /home/app/application/ ./
