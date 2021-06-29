FROM openjdk:11-jdk-slim
COPY "./build/libs/service-photo-0.0.1-SNAPSHOT.jar" "service-photo.jar"
EXPOSE 8089
ENTRYPOINT ["java","-jar","service-photo.jar"]