FROM maven:3.6.0-jdk-11-slim AS build

COPY src /home/app/src

COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM openjdk:11

EXPOSE 8080

COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar

ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]