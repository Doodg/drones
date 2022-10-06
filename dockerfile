FROM openjdk:11.0.7
ADD build/libs/drones-0.0.1-SNAPSHOT.jar drones.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","drones.jar"]
