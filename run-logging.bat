./mvnw -T 2C -pl services/logging clean spring-boot:run ^
"-Dspring-boot.run.jvmArguments=-Xms20m -Xmx128m"
