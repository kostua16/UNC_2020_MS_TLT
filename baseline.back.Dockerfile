FROM maven:3.6-alpine AS deps
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
RUN mvn de.qaware.maven:go-offline-maven-plugin:resolve-dependencies

FROM maven:3.6-alpine AS build
COPY --from=deps /root/.m2/ /root/.m2/

