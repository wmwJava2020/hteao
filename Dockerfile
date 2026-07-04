FROM eclipse-temurin:17-jdk-alpine

EXPOSE 1972

ADD target/HTEAO-0.0.1-SNAPSHOT.jar /app/app.jar

#WORKDIR /app
<<<<<<< HEAD:Dockerfile/

ENTRYPOINT ["java", "-jar", "/app/hteao-0.0.1-snapshot.jar']
=======
#Testing....
ENTRYPOINT ["java", "-jar", "/HTEAO-0.0.1-SNAPSHOT.jar']
>>>>>>> 89eeefadbacfa9ab03ae1b0bc3ac95fcd4137146:DockerFile

<<<<<<<
<<<<<