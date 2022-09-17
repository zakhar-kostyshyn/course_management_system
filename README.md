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
After successful Run open [Swagger](http://localhost:8883/swagger)

### Backend on Host with Database in Docker

Start DB in docker:
```
docker-compose -f docker-compose-db.yml up -d
```

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

## Production AWS CloudWatch links
* [Production Build Logs](https://eu-central-1.console.aws.amazon.com/cloudwatch/home?region=eu-central-1#logsV2:log-groups/log-group/$252Faws$252Fcodebuild$252Fcourse-management-system-pipeline)
* [Production Deploy Logs](https://eu-central-1.console.aws.amazon.com/cloudwatch/home?region=eu-central-1#logsV2:log-groups/log-group/$252Fapp$252Fcourse_management_system$252Fapp.log)

## GitHub page
[GitHub](https://github.com/zakhar-kostyshyn/course_management_system)


