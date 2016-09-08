#!/bin/bash
args=("$@")
OPEN_LAVA_MASTER=$(hostname | cut -d. -f1)
CLUSTER="gpu"
JOB_NAME=${args[0]}
SCRIPTS_ZIP=${args[1]}
MAIN_FILE=${args[2]}
CONFIG_FILE=${args[3]} #"config.txt"
i=0

#TODO add check and exit codes to prevent some misbehaviours
function R_submit {
  echo "scp $OPEN_LAVA_MASTER:~/$SCRIPTS_ZIP .;
        unzip $SCRIPTS_ZIP -d $JOB_NAME;
        mkdir $JOB_NAME/results;
        $R_submit;
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
    R_submit="R ~/$JOB_NAME/$MAIN_FILE  $line"
    result_zip="results_"$JOB_NAME"_"$i".zip"
    submit=$(gpu_submit)
    bsub -q "$CLUSTER" -J "$JOB_NAME"_"$i" -r "$submit"
   done < $CONFIG_FILE
  exit 0;
fi