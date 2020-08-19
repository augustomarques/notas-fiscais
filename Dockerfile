FROM maven:3-jdk-11 as builder
ADD . /app
WORKDIR /app
RUN ls -l
RUN mvn clean install

FROM openjdk:11-jdk
VOLUME /tmp
COPY --from=builder "/app/target/notasfiscais-*-SNAPSHOT.jar" app.jar
CMD java $JAVA_OPTS -jar app.jar