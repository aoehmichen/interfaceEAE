#!/bin/bash

args=("$@")
OPEN_LAVA_MASTER=$(hostname | cut -d. -f1)
HOSTS=($(<spark_cluster.txt))
JOB_NAME=${args[0]}
SCRIPTS_ZIP=${args[1]}
PYTHON_MAIN_FILE=${args[2]}
i=0

parallel-rsync -h destfile.txt -p 10 $SCRIPTS_ZIP  ~/

echo "$JOB_NAME"

echo "$SCRIPTS_ZIP"

if [ $? -eq 0 ]
 then
  while read line;
   do
    python_submit="export LD_LIBRARY_PATH=/usr/local/cuda/lib64; export CUDA_HOME=/usr/local/cuda; python ~/$JOB_NAME/$PYTHON_MAIN_FILE  $line"
    result_zip="results_"$JOB_NAME"_"${HOSTS[$i]}".zip"
    echo "$JOB_NAME"
    #bsub -m "$OPEN_LAVA_MASTER" -J "task1"${HOSTS[$i]} "rcp $SCRIPTS_ZIP ${HOSTS[$i]}:~;"
    bsub -m "${HOSTS[$i]}" -J "$JOB_NAME"_"${HOSTS[$i]}"  "unzip $SCRIPTS_ZIP -d $JOB_NAME; mkdir $JOB_NAME/results; $python_submit; cd ~/$JOB_NAME/results/; zip -r $result_zip *; scp $result_zip $OPEN_LAVA_MASTER:~/results/; rm -rf ~/$JOB_NAME;"
    i=$((i+1))
    if [ $i = ${#HOSTS[@]} ]; then
       break;
    fi
   done < config.txt
  exit $$;

else
  echo "The parallel rsync failed. Error code: $?" ;
fi
