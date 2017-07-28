#!/bin/bash
args=("$@")
OPEN_LAVA_MASTER=$(hostname | cut -d. -f1)


source /etc/profile.d/openlava.sh

bkill -q "spark"
bkill -q "spark"
bkill -q "spark"
bkill -q "spark"