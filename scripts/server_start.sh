#!/usr/bin/env bash
cd /app/course_management_system
sudo java -jar -Dserver.port=80 \
    *.jar > /dev/null 2> /dev/null < /dev/null &