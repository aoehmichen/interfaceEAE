#!/bin/bash
args=("$@")
OPEN_LAVA_MASTER=$(hostname | cut -d. -f1)
CLUSTER=${args[0]}
JOB_NAME=${args[1]}
SCRIPTS_ZIP_ON_REMOTE_HOST=${args[2]}
MAIN_FILE=${args[3]}
CONFIG_FILE=${args[4]} #"config.txt"
REMOTE_HOST=${args[5]}
i=0

SCRIPTS_ZIP=$JOB_NAME".zip"

#TODO add check and exit codes to prevent some misbehaviours
function python_submit_function {
  echo "mkdir -p /tmp/$JOB_NAME;
        cd /tmp/$JOB_NAME;
        scp $REMOTE_HOST:$SCRIPTS_ZIP_ON_REMOTE_HOST .;
        unzip $SCRIPTS_ZIP -d /tmp/$JOB_NAME;
        mkdir -p /tmp/$JOB_NAME/results;
        $python_submit
        cd /tmp/$JOB_NAME/results/;
        zip -r $result_zip *;
        ssh $REMOTE_HOST 'mkdir -p /home/ubuntu/jupyter/eae_results_$JOB_NAME/'
        scp $result_zip $REMOTE_HOST:/home/ubuntu/jupyter/eae_results_$JOB_NAME/;
        rm -rf /tmp/$JOB_NAME;"
}

while read line;
  do
    python_submit="python /tmp/$JOB_NAME/$MAIN_FILE  $line"
    result_zip="results_"$JOB_NAME"_"$i".zip"
    submit=$(python_submit_function)
    bsub -q "$CLUSTER" -J "$JOB_NAME"_"$i" -r "$submit"
  done < $CONFIG_FILE
exit 0;
