#!/usr/bin/env bash
export DATABASE_DB=$(aws ssm get-parameters --output text --region eu-central-1 --names DATABASE_DB --query Parameters[0].Value)
export DATABASE_HOST=$(aws ssm get-parameters --output text --region eu-central-1 --names DATABASE_HOST --query Parameters[0].Value)
export DATABASE_PASSWORD=$(aws ssm get-parameters --output text --region eu-central-1 --names DATABASE_PASSWORD --query Parameters[0].Value)
export DATABASE_PORT=$(aws ssm get-parameters --output text --region eu-central-1 --names DATABASE_PORT --query Parameters[0].Value)
export DATABASE_USERNAME=$(aws ssm get-parameters --output text --region eu-central-1 --names DATABASE_USERNAME --query Parameters[0].Value)
export SPRING_SERVER_PORT=$(aws ssm get-parameters --output text --region eu-central-1 --names SPRING_SERVER_PORT --query Parameters[0].Value)
export SWAGGER_URL=$(aws ssm get-parameters --output text --region eu-central-1 --names SWAGGER_URL --query Parameters[0].Value)