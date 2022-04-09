FROM gradle:jdk17

WORKDIR /workspace

COPY src src
COPY build.gradle .
COPY settings.gradle .

RUN gradle build --no-daemon -x test

FROM openjdk:17-alpine

EXPOSE 8080

RUN mkdir /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=0 /workspace/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java" ,"-jar", "/app/app.jar"]