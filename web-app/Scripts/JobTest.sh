#!/bin/bash
args=("$@")
OPEN_LAVA_MASTER=$(hostname | cut -d. -f1)
CLUSTER="gpu"
JOB_NAME=${args[0]}
SCRIPTS_ZIP=${args[1]}
MAIN_FILE=${args[2]}
CONFIG_FILE=${args[3]} #"config.txt"
i=0

echo $JOB_NAME
echo $MAIN_FILE

if [ -d ~/results_$JOB_NAME ]
 then
  echo "The folder already exists for job: $JOB_NAME"
  exit 1;
 else
  mkdir  ~/results_$JOB_NAME
  while read line;
   do
    python_submit="python ~/$JOB_NAME/$MAIN_FILE  $line"

    echo $python_submit > toto.txt
   done < $CONFIG_FILE

  exit 0;
fi