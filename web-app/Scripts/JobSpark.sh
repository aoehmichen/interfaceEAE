#!/bin/bash
args=("$@")
OPEN_LAVA_MASTER=$(hostname | cut -d. -f1)
CLUSTER="spark"
JOB_NAME=${args[0]}
SCRIPTS_ZIP=${args[1]}
MAIN_FILE=${args[2]}
CONFIG_FILE=${args[3]} #"config.txt"
i=0


#TODO add check and exit codes to prevent some misbehaviours
function spark_submit {
  echo "scp $OPEN_LAVA_MASTER:~/$SCRIPTS_ZIP .;
        unzip $SCRIPTS_ZIP -d $JOB_NAME;
        mkdir $JOB_NAME/results;
        $exports
        $python_submit
        cd ~/$JOB_NAME/results/;
        zip -r $result_zip *;
        scp $result_zip $OPEN_LAVA_MASTER:~/results_$JOB_NAME/;
        rm -rf ~/$JOB_NAME;"
}


if [ -d ~/results_$JOB_NAME ]
 then
  echo "The folder already exists for job: $JOB_NAME"
  exit 1;
 else
  mkdir  ~/results_$JOB_NAME
  while read line;
   do
    spark_submit="/usr/bin/spark-submit --py-files eAE.zip --master yarn-client --num-executors 5 --driver-memory 1024m --executor-memory 512m --executor-cores 1 $line"
    result_zip="results_"$JOB_NAME"_"$i".zip"
    submit=$(spark_submit)
    bsub -q "$CLUSTER" -J "$JOB_NAME"_"$i" -r "$submit"
    #job=$(bsub -q "gpu" -J "$JOB_NAME"_"$i" -r "$submit")
    #job_id=( $(echo  "$job" | xargs -n 1 | grep "<[0-9]" | cut -b2- | sed s"/.$//" ))

    #i=$((i+1))
#    if [ -z $ALL_JOBS ]
#    then
#        ALL_JOBS="ended($job_id)";
#    else
#        ALL_JOBS=$ALL_JOBS"&&ended($job_id)";
#    fi
   done < $CONFIG_FILE
   #bsub -w "$ALL_JOBS" -J "Cleanup"  "sleep 20;"
  exit 0;
fi