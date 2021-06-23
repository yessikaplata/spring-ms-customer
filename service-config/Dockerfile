FROM openjdk:11-jdk-slim
COPY "./build/libs/service-config-0.0.1-SNAPSHOT.jar" "service-config.jar"
EXPOSE 8090
ENTRYPOINT ["java","-jar","service-config.jar"]