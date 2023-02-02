FROM openjdk:11

EXPOSE 8080

ADD ./target/*.jar app.jar

ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]
