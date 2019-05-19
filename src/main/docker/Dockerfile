FROM openjdk:jdk-alpine

RUN apk update && apk upgrade
RUN mkdir -p /usr/local/spring-devops
ADD @project.build.finalName@.jar /usr/local/spring-devops/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh