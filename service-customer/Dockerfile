FROM openjdk:11-jdk-slim
COPY "./build/libs/service-customer-0.0.1-SNAPSHOT.jar" "service-customer.jar"
EXPOSE 8088
ENTRYPOINT ["java","-jar","service-customer.jar"]