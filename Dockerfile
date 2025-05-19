FROM maven:3.8-openjdk-17 AS build
WORKDIR /workspace/app

COPY pom.xml .
COPY src src

# Build the application
RUN mvn install -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
VOLUME /tmp
COPY --from=build /workspace/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"] 