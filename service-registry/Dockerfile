FROM openjdk:11-jdk-slim
COPY "./build/libs/service-registry-0.0.1-SNAPSHOT.jar" "service-registry.jar"
EXPOSE 8091
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","service-registry.jar"]