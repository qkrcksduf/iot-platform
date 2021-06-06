FROM openjdk:11-jdk

WORKDIR /

ADD ./build/libs/api-gateway-*.jar app.jar

EXPOSE 8000

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
