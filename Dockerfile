FROM openjdk:17
ADD target/MediaSoftTestTask-0.0.1-SNAPSHOT.jar MediaSoftTestTask-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","MediaSoftTestTask-0.0.1-SNAPSHOT.jar"]