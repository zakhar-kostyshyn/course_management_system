FROM openjdk:11

COPY gradlew /cms/gradlew
COPY build.gradle /cms/build.gradle
COPY gradlew.bat /cms/gradlew.bat
COPY settings.gradle /cms/settings.gradle

COPY gradle /cms/gradle
COPY src /cms/src

WORKDIR cms

RUN ./gradlew clean build

EXPOSE 8881

WORKDIR build/libs

RUN chmod +x promotion-0.0.1-SNAPSHOT.jar

CMD java -jar promotion-0.0.1-SNAPSHOT.jar
