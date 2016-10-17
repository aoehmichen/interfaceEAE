#!/bin/bash
args=("$@")
OPEN_LAVA_MASTER=$(hostname | cut -d. -f1)
CLUSTER="spark"
JOB_NAME=${args[0]}
SCRIPTS_ZIP=${args[1]}
eAE_FOLDER=${args[2]}
MAIN_FILE=${args[3]}
CONFIG_FILE=${args[4]} #"config.txt"

i=0
MAIN_FILE_ZIP=$MAIN_FILE".zip"

#TODO add check and exit codes to prevent some misbehaviours
function spark_submit {
  echo "mkdir -p /tmp/$JOB_NAME;
        scp $OPEN_LAVA_MASTER:$SCRIPTS_ZIP /tmp/$JOB_NAME;
        scp $OPEN_LAVA_MASTER:$MAIN_FILE_ZIP /tmp/$JOB_NAME;
        unzip /tmp/$JOB_NAME/$SCRIPTS_ZIP;
        unzip /tmp/$JOB_NAME/$MAIN_FILE_ZIP;
        hadoop fs -put /tmp/$JOB_NAME/*;
        $spark_submit
        rm -rf /tmp/$JOB_NAME;
        hadoop fs -rm *;"
}

if [ ! -f $CONFIG_FILE ]
 then
  echo "The Config file doesn't for job: $JOB_NAME"
  exit 1;
else
  while read line;
   do
    spark_submit="/usr/bin/spark-submit --py-files $MAIN_FILE_ZIP --master yarn-client --num-executors 5 --executor-cores 16 --driver-memory 20g --executor-memory 20g $MAIN_FILE.py $line"
    submit=$(spark_submit)
    bsub -q "$CLUSTER" -J "Transmart_$JOB_NAME_$i" -r "$submit"
   done < $CONFIG_FILE
  exit 0;
fi