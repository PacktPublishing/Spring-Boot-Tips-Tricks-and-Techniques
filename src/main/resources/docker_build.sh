#!/usr/bin/env bash
mvn clean package docker:build -DskipTests
docker images --all
docker run hands-on-spring-packt-docker-image
docker ps
docker exec -it 23f049b57f9f curl http://localhost:8080/application/health