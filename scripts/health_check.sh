#!/bin/bash
while true
do
    if [ "$(curl -s http://localhost:8088/healthcheck)" = 'Course Management System Works' ]
    then
        echo "Healthcheck passed"
        exit 0
    else
        echo "Server is not running..."
        sleep 10s
    fi
done
