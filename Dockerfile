FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/adressbuchs-0.0.1-SNAPSHOT.jar adressbuchs.jar
ENTRYPOINT ["java","-jar","adressbuchs.jar"]