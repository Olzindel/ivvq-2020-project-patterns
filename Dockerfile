FROM maven:3-jdk-8 as maven

WORKDIR /app

COPY back_end/pom.xml ./pom.xml

RUN mvn dependency:go-offline -B

COPY back_end/src ./src

RUN mvn package && cp target/patterns-*.jar app.jar

FROM openjdk:8-jre-alpine

WORKDIR /app

COPY --from=maven /app/app.jar ./app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]