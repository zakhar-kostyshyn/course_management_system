#!/usr/bin/env bash
cd /app/course_management_system
java -jar -Dserver.port=80 \
    *.jar > /dev/null 2> /dev/null < /dev/null &