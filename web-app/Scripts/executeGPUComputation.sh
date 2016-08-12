#!/bin/bash
args=("$@")
OPEN_LAVA_MASTER=$(hostname | cut -d. -f1)
HOSTS=($(<spark_cluster.txt))
JOB_NAME=${args[0]}
SCRIPTS_ZIP=${args[1]}
PYTHON_MAIN_FILE=${args[2]}
i=0

while read line;
 do
   python_submit="export LD_LIBRARY_PATH=/usr/local/cuda/lib64; export CUDA_HOME=/usr/local/cuda; python ~/$JOB_NAME/$PYTHON_MAIN_FILE  $line"
   result_zip="results_"$JOB_NAME"_"${HOSTS[$i]}".zip"
   #echo "$result_zip"
   #echo "$python_submit"
   bsub -m "$OPEN_LAVA_MASTER" -J "task1"${HOSTS[$i]} "rcp $SCRIPTS_ZIP ${HOSTS[$i]}:~;"
   bsub -m "${HOSTS[$i]}" -J "task2"${HOSTS[$i]} -w "done(task1${HOSTS[$i]})"  "unzip $SCRIPTS_ZIP -d $JOB_NAME;"
   bsub -m "${HOSTS[$i]}" -J "task3"${HOSTS[$i]} -w "done(task2${HOSTS[$i]})"  "mkdir $JOB_NAME/results;"
   bsub -m "${HOSTS[$i]}" -J "task4"${HOSTS[$i]} -w "done(task3${HOSTS[$i]})"  $python_submit
   bsub -m "${HOSTS[$i]}" -J "task5"${HOSTS[$i]} -w "done(task4${HOSTS[$i]})"  "cd ~/$JOB_NAME/results/; zip $result_zip *; scp $result_zip $OPEN_LAVA_MASTER:~/results/"
   bsub -m "${HOSTS[$i]}" -J "task6"${HOSTS[$i]} -w "done(task5${HOSTS[$i]})"  "rm -rf $JOB_NAME"
   echo ${HOSTS[$i]}
   i=$((i+1))
   if [ $i = ${#HOSTS[@]} ]; then
      break;
   fi
done < config.txt



