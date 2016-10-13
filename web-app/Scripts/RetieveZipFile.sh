#!/usr/bin/env bash

args=("$@")
ZIP_FILE_NAME=${args[0]}
HOST=${args[1]}
LOCAL_DATA_STORE=${args[2]}

scp dsigdo@$HOST:/tmp/eae/$ZIP_FILE_NAME $LOCAL_DATA_STORE