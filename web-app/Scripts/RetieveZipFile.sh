#!/usr/bin/env bash

args=("$@")
ZIP_FILE_NAME=${args[0]}
HOST=${args[1]}
LOCAL_DATA_STORE=${args[2]}
UUID=${args[3]}

mkdir $LOCAL_DATA_STORE/"Job-"$UUID

scp dsigdo@$HOST:/tmp/$ZIP_FILE_NAME $LOCAL_DATA_STORE/"Job-"$UUID