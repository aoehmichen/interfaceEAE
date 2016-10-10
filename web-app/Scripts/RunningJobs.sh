#!/usr/bin/env bash

# We load the OpenLava Env Variables
args=("$@")
OPENLAVA_ENV_VAR_LOCATION=${args[0]}

source $OPENLAVA_ENV_VAR_LOCATION

bjobs