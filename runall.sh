#!/usr/bin/env bash

#clean anything with same name to get rid of clashes
docker-compose down

./downloads.sh

#update with actual password
echo "YOUR_PASSWORD_HERE" > ./docker/artifactory/secrets/artifactoryPassword

#update older jenkins image, make sure it doesnt use cache
docker-compose build --no-cache

#run all
docker-compose up