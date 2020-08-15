FROM maven:3.6.3-jdk-8 as MAVEN_BUILD

# optimize by downloading all dependencies first to reuse layer
WORKDIR /build/
COPY pom.xml /build/
RUN mvn dependency:go-offline -B

# build project
COPY src /build/src
RUN mvn package

# run packaged jar file in slimmer container
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]