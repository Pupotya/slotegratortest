#!/bin/bash

if [ "$#" -ne 3 ]; then
  echo "Usage: ./run.sh <log_level> <email> <password>"
  exit 1
fi

IMAGE_NAME=slotegratortest
LOG_LEVEL=$1
EMAIL=$2
PASSWORD=$3

docker build -f deployment/docker/Dockerfile -t $IMAGE_NAME .

CONTAINER_ID=$(docker run -d -v gradle-cache:/home/gradle/.gradle $IMAGE_NAME sh -c "./gradlew test --no-daemon -DLOG_LEVEL=$LOG_LEVEL -DTEST_EMAIL=$EMAIL -DTEST_PASSWORD=$PASSWORD && sleep 10")
echo "Container started: $CONTAINER_ID"
docker logs -f "$CONTAINER_ID"

docker cp "$CONTAINER_ID":/app/build/allure-results ./allure-results

docker rm $IMAGE_NAME

allure serve ./allure-results