#!/bin/sh

echo "********************************************************"
echo "Starting Spring-DevOps"
echo "********************************************************"
java -Dspring.profiles.active=$PROFILE -jar /usr/local/spring-devops/@project.build.finalName@.jar
