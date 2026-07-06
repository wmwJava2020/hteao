FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 1972

HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget -qO- http://localhost:1972/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]