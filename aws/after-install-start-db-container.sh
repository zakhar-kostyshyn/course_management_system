#!/bin/bash
docker run -d --name cms_db -p 5432:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=root POSTGRES_DB=cms_db postgres:14.1-alpine