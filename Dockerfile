FROM amazoncorretto:11-alpine-jdk
VOLUME /tmp
COPY target/photo-backend-users-api-0.0.1-SNAPSHOT.jar UsersService.jar
ENTRYPOINT ["java", "-jar", "UsersService.jar"]