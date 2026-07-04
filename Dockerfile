FROM eclipse-temurin:17-jdk-alpine

EXPOSE 1972

ADD target/HTEAO-0.0.1-SNAPSHOT.jar hteao-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "hteao-0.0.1-snapshot.jar"]

#WORKDIR /app
