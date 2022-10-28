FROM adoptopenjdk/openjdk16:alpine-jre

WORKDIR /usr/app/

RUN mkdir /usr/app/images

RUN apk --no-cache upgrade

COPY build/libs/mbn-service-1.0-SNAPSHOT.jar mbn-service.jar

COPY src/main/resources/static/documents/GDPR/GDPR.pdf GDPR.pdf

CMD java  -XX:MaxRAMPercentage=75 -XX:+UseG1GC -jar mbn-service.jar