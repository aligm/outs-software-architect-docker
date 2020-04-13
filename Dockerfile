FROM maven:3.6.3-jdk-8 as builder
ARG SRCDIR=/usr/src/app-sources
COPY . $SRCDIR
WORKDIR $SRCDIR
RUN mvn clean package

FROM openjdk:8u242-jre-slim-buster
ARG APPNAME=docker-0-0.0.1-SNAPSHOT.jar
ARG BUILDER_APP_PATH=/usr/src/app-sources/target/$APPNAME
COPY --from=builder $BUILDER_APP_PATH /usr/src/healthapp/
WORKDIR /usr/src/healthapp/
EXPOSE 8000
ENV APPNAME=$APPNAME
CMD java -jar $APPNAME
