#!/usr/bin/env bash
cd /app/course_management_system || exit
sudo java -Dspring.profiles.active=prod -jar \
 -DDATABASE_DB="$(aws ssm get-parameters --output text --region eu-central-1 --names DATABASE_DB --query Parameters[0].Value)" \
 -DDATABASE_HOST="$(aws ssm get-parameters --output text --region eu-central-1 --names DATABASE_HOST --query Parameters[0].Value)" \
 -DDATABASE_PASSWORD="$(aws ssm get-parameters --output text --region eu-central-1 --names DATABASE_PASSWORD --query Parameters[0].Value)" \
 -DDATABASE_PORT="$(aws ssm get-parameters --output text --region eu-central-1 --names DATABASE_PORT --query Parameters[0].Value)" \
 -DDATABASE_USERNAME="$(aws ssm get-parameters --output text --region eu-central-1 --names DATABASE_USERNAME --query Parameters[0].Value)" \
 -DSPRING_SERVER_PORT="$(aws ssm get-parameters --output text --region eu-central-1 --names SPRING_SERVER_PORT --query Parameters[0].Value)" \
 -DSWAGGER_URL="$(aws ssm get-parameters --output text --region eu-central-1 --names SWAGGER_URL --query Parameters[0].Value)" \
 -DSECURITY_SECRET="$(aws ssm get-parameters --output text --region eu-central-1 --names SECURITY_SECRET --query Parameters[0].Value)" \
 promotion-0.0.1-SNAPSHOT.jar
