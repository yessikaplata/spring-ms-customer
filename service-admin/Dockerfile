FROM openjdk:11-jdk-slim
COPY "./build/libs/service-admin-0.0.1-SNAPSHOT.jar" "service-admin.jar"
EXPOSE 8093
ENTRYPOINT ["java","-jar","service-admin.jar"]