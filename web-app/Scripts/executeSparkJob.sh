#!/usr/bin/env bash

args=("$@")
STR=""

for i in $(seq 0 $#)
 do
  STR+=" ${args[$i]}"
 done

#command="nohup /usr/bin/spark-submit --py-files /home/ubuntu/CrossValidation.zip --master yarn-client --num-executors 2 --driver-memory 1024m --executor-memory 512m --executor-cores 1 $STR >/dev/null 2>&1 &"
command="/usr/bin/spark-submit --py-files /home/ubuntu/CrossValidation.zip --master yarn-client --num-executors 2 --driver-memory 1024m --executor-memory 512m --executor-cores 1 $STR"

sudo -u ubuntu  $command &
