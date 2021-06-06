FROM openjdk:11-jdk
MAINTAINER cheolho@edu.hanbat.ac.kr

WORKDIR /
ADD ./build/libs/actuating-*.jar app.jar

EXPOSE 8004

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

