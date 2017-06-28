#!/bin/bash
args=("$@")
OPEN_LAVA_MASTER=$(hostname | cut -d. -f1)
CLUSTER=${args[0]}
JOB_NAME=${args[1]}
SCRIPTS_ZIP_ON_REMOTE_HOST=${args[2]}
MAIN_FILE=${args[3]}
CONFIG_FILE=${args[4]}
DOCKER_HOST=${args[5]}
DOCKER_SSH_PORT=${args[6]}
i=0

SCRIPTS_ZIP=$JOB_NAME".zip"

source /etc/profile.d/openlava.sh

#TODO add check and exit codes to prevent some misbehaviours
#NB: the mkdir -p /tmp/$JOB_NAME/$MAIN_FILE; is useless at the moment but might be useful in the future
function spark_submit {
  echo "mkdir -p /tmp/$JOB_NAME;
        cd /tmp/$JOB_NAME;
        scp -P $DOCKER_SSH_PORT $DOCKER_HOST:$SCRIPTS_ZIP_ON_REMOTE_HOST .;
        while [ ! -e "/tmp/$JOB_NAME/$SCRIPTS_ZIP" ]; do
            echo 'Attempting to transfer the Zip file again.' ;
            sleep 15;
            scp -P $DOCKER_SSH_PORT $DOCKER_HOST:$SCRIPTS_ZIP_ON_REMOTE_HOST .;
        done
        scp $OPEN_LAVA_MASTER:putToHDFS.sh .;
        mkdir -p /tmp/$JOB_NAME/results;
        mkdir -p /tmp/$JOB_NAME/main_file;
        mkdir -p /tmp/$JOB_NAME/data;
        unzip $SCRIPTS_ZIP -d /tmp/$JOB_NAME/data;
        mv /tmp/$JOB_NAME/data/$MAIN_FILE /tmp/$JOB_NAME;
        bash putToHDFS.sh $JOB_NAME main_file;
        $spark_submit
        cd /tmp/$JOB_NAME/results/;
        zip -r $result_zip *;
        ssh -p $DOCKER_SSH_PORT $DOCKER_HOST 'mkdir -p /home/eae/jupyter/eae_results_$JOB_NAME/';
        scp -P $DOCKER_SSH_PORT $result_zip $DOCKER_HOST:/home/eae/jupyter/eae_results_$JOB_NAME/;
        rm -rf /tmp/$JOB_NAME;"
}

while read line;
 do
  spark_submit="/usr/bin/spark-submit --master yarn-client --num-executors 5 --driver-memory 1024m --executor-memory 512m --executor-cores 1 '/tmp/$JOB_NAME/$MAIN_FILE' $line"
  result_zip="results_"$JOB_NAME"_"$i".zip"
  submit=$(spark_submit)
  bsub -q "$CLUSTER" -J "$JOB_NAME"_"$i" -r "$submit"
  i=$((i+1))
 done < $CONFIG_FILE
exit 0;

rm -rf $CONFIG_FILE