FROM openjdk:11-jdk-slim
COPY "./build/libs/service-gateway-0.0.1-SNAPSHOT.jar" "service-gateway.jar"
EXPOSE 8092
ENTRYPOINT ["java","-jar","service-gateway.jar"]