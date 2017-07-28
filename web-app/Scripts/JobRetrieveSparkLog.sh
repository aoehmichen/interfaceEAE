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
function copy_log {
  echo "scp -P $DOCKER_SSH_PORT /var/mail/dsigdo $DOCKER_HOST:/home/eae/jupyter/;
        rm -rf /var/mail/dsigdo;"
}

submit=$(copy_log)
bsub -q "$CLUSTER" -J "$JOB_NAME" -r "$submit"

exit 0;

