FROM node:13-alpine as frontend

WORKDIR /app

COPY ./frontend/package.json ./

RUN npm install

COPY ./frontend ./

RUN npm run build

#========================================================================

FROM maven:3-jdk-8 as backend

WORKDIR /app

COPY pom.xml ./pom.xml

WORKDIR /app/backend

COPY ./backend/pom.xml ./

RUN mvn dependency:go-offline -B

COPY ./backend/src ./src/
COPY --from=frontend /app/target/dist ./src/main/resources/public/

RUN mvn package && cp ./target/backend-*.jar ../app.jar

#========================================================================

FROM openjdk:8-jre-alpine

WORKDIR /app

COPY --from=backend /app/app.jar ./app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]