#FROM maven:3.9.9-eclipse-temurin-21 AS builder
#
#WORKDIR /app
#
#COPY pom.xml .
#
#RUN mvn dependency:go-offline -B
#
#COPY src ./src
#
#RUN mvn clean package -DskipTests
#
#FROM eclipse-temurin:17-jdk-jammy AS runner
#
#WORKDIR /app
#
#COPY --from=builder /app/target/Shopping-Cart-0.0.1-SNAPSHOT.jar /app/app.jar
#
#EXPOSE 9090
#
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/Shopping-Cart-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9090

CMD ["java", "-jar", "app.jar"]