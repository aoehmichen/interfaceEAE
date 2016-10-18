#!/bin/bash

args=("$@")
JOB_NAME=${args[0]}
MAIN_FILE=${args[1]}
MAIN_FILE_ZIP=$MAIN_FILE".zip"

export JAVA_HOME="/etc/alternatives/java"

hadoop fs -put /tmp/$JOB_NAME/$MAIN_FILE_ZIP;

for f in /tmp/$JOB_NAME/$MAIN_FILE/*;
 do
   hadoop fs -put $f;
done;