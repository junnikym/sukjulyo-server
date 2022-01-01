FROM openjdk:11-jre
MAINTAINER HyeongJun Kim <junnikym@gmail.com>

VOLUME /tmp/sukjulyo
EXPOSE 8080

ARG JAR_FILES=build/libs/*.war
COPY ${JAR_FILES} app.war
ENTRYPOINT ["java", "-jar", "/app.war"]