#!/bin/bash
while true
do
    if [ "$(curl -s http://localhost:8088/healthcheck)" = 'Course Management System Works' ]
    then
        exit 0
    else
        echo "check server is running?"
        sleep 3s
    fi
done
