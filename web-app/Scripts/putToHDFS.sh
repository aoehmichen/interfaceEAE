#!/bin/bash

args=("$@")
JOB_NAME=${args[0]}
MAIN_FILE=${args[1]}


for f in /tmp/$JOB_NAME/$MAIN_FILE/*;
 do
   hadoop fs -put $f;
done;