#!/usr/bin/env bash

# We load the OpenLava Env Variables
OPENLAVA_ENV_VAR_LOCATION=${args[0]}

$OPENLAVA_ENV_VAR_LOCATION

bqueues -l