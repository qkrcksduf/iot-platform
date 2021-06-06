FROM openjdk:11-jdk
MAINTAINER cheolho@edu.hanbat.ac.kr

WORKDIR /
ADD ./build/libs/account-*.jar app.jar

EXPOSE 8001

ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]