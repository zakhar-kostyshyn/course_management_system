# Course Management System

## Description
Course Management System API is an online management application. 
Its main purpose is to make efficient interaction between 
students and instructors in college during the period of submission of assignments and for getting appropriate feedback from instructors.

## Run project

### In Docker
Start Backend and Database in docker:
```
docker-compose up
```

### Backend on Host with Database in Docker

Start DB in docker:
```
docker-compose -f docker-compose-db.yml up -d
```
After successful Run open [Swagger](http://localhost:8883/swagger)

Run BE:
```
./gradlew bootRun
```
After successful Run open [Swagger](http://localhost:8882/swagger)

## Test
Run tests
```
./gradlew test
```

## Deploy changes
* Create new branch from `master`;
* Make changes in new branch;
* Merge feature branch into `master`;
* Push `master`;
* AWS CodePipeline will catch GitHub webhook and run deployment;

## Production Swagger endpoint:
[Endpoint](http://ec2-18-193-106-115.eu-central-1.compute.amazonaws.com:8088/swagger-ui/index.html)

## GitHub page
[GitHub](https://github.com/zakhar-kostyshyn/course_management_system)


