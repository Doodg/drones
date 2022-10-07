FROM gradle:7-jdk11-jammy AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

FROM openjdk:11.0.7
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/drones-0.0.1-SNAPSHOT.jar /app/drones.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/drones.jar"]