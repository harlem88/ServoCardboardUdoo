#!/bin/bash
direction=$1
txt='{"direction":'\"$1\"'}'
 
curl -i \
        -H "Accept: application/json" \
        -H "Content-Type:application/json" \
        -X POST -d "$txt" "localhost:8888/api/direction"