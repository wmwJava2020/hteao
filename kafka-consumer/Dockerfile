FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8083

HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget -qO- http://localhost:8083/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
