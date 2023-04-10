#!/bin/bash

# Pull new changes
git pull

# Prepare Jar
mvn clean
mvn package

# Ensure, that docker-compose stopped
docker-compose stop

# Add environment variables
export DB_USERNAME=postgres
export DB_PASSWORD=9876543219
export DB_URL=jdbc:postgresql://postgres:5432/lbDB

# Start new deployment
docker-compose up --build -d
