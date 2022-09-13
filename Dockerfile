FROM eclipse-temurin:11-alpine

RUN mkdir -p /opt/promotion

WORKDIR /opt/promotion
COPY gradle ./gradle
COPY gradlew ./gradlew
COPY build.gradle ./build.gradle
COPY docker-compose-build.yml ./docker-compose-build.yml

EXPOSE 8881

CMD ./gradlew --stacktrace --no-daemon bootRun -x databaseComposeUp generateJooq
