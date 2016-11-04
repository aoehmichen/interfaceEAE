#!/bin/bash
args=("$@")
OPEN_LAVA_MASTER=$(hostname | cut -d. -f1)
CLUSTER="spark"
JOB_NAME=${args[0]}
DATA_ZIP="${args[1]}"
eAE_FOLDER=${args[2]}s
MAIN_FILE=${args[3]}
CONFIG_FILE=${args[4]}

i=0
MAIN_ADDITIONALFILES_ZIP=$MAIN_FILE".zip"
MAIN_FILE_PY=$MAIN_FILE".py"

source /etc/profile.d/openlava.sh

#TODO add check and exit codes to prevent some misbehaviours
function spark_submit_function {
  echo "hadoop fs -rm -f -R /user/dsigdo/*;
        mkdir -p /tmp/$JOB_NAME;
        cd /tmp/$JOB_NAME;
        if [ "$DATA_ZIP" != "None" ];
        then
            scp $OPEN_LAVA_MASTER:$DATA_ZIP /tmp/$JOB_NAME/;
            mkdir /tmp/$JOB_NAME/data/;
            unzip *.zip -d /tmp/$JOB_NAME/data;
        fi;
        scp $OPEN_LAVA_MASTER:AnalyticsEAE/$MAIN_FILE/$MAIN_FILE_PY /tmp/$JOB_NAME;
        scp $OPEN_LAVA_MASTER:AnalyticsEAE/eAE.zip /tmp/$JOB_NAME;
        scp $OPEN_LAVA_MASTER:AnalyticsEAE/$MAIN_FILE/$MAIN_ADDITIONALFILES_ZIP /tmp/$JOB_NAME;
        scp $OPEN_LAVA_MASTER:putToHDFS.sh /tmp/$JOB_NAME;
        unzip /tmp/$JOB_NAME/$MAIN_ADDITIONALFILES_ZIP -d /tmp/$JOB_NAME/;
        unzip -n /tmp/$JOB_NAME/eAE.zip -d /tmp/$JOB_NAME/;
        bash putToHDFS.sh $JOB_NAME $MAIN_FILE;
        $spark_submit;
        rm -rf /tmp/$JOB_NAME;"
}

if [ ! -f $CONFIG_FILE ]
 then
  echo "The Config file doesn't for job: $JOB_NAME"
  exit 1;
else
  while read line;
   do
    spark_submit="/usr/bin/spark-submit --py-files /tmp/$JOB_NAME/eAE.zip --master yarn-client --num-executors 5 --executor-cores 16 --driver-memory 20g --executor-memory 20g /tmp/$JOB_NAME/$MAIN_FILE.py $line"
    submit=$(spark_submit_function)
    bsub -q "$CLUSTER" -J "Transmart_"$JOB_NAME"_$i" -r $submit
   done < $CONFIG_FILE
  exit 0;
fi

rm -rf $CONFIG_FILE