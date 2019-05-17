FROM openjdk:8-jdk

ADD build/libs/patients-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT java -jar app.jar