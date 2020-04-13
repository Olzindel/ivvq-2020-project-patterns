FROM maven:3-jdk-8 as build

WORKDIR /app

COPY pom.xml ./pom.xml
COPY ./backend/pom.xml ./backend/
COPY ./frontend/pom.xml ./frontend/

RUN mvn dependency:go-offline -B

COPY ./backend/src/. ./backend/src/
COPY ./frontend/babel.config.js ./frontend/package.json ./frontend/
COPY ./frontend/src/. ./frontend/src/

RUN mvn package && cp ./backend/target/backend-*.jar app.jar

FROM openjdk:8-jre-alpine

WORKDIR /app

COPY --from=build /app/app.jar ./app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]