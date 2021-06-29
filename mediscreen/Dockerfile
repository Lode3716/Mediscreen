FROM adoptopenjdk/openjdk15

ARG JAR_FILE=target/mediscreen-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} mediscreen.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/mediscreen.jar"]
