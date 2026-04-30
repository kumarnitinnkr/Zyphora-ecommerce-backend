FROM eclipse-temurin:21

WORKDIR /app

COPY target/zyphora-backend.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]