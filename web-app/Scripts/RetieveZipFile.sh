#!/usr/bin/env bash

args=("$@")
ZipFileName=${args[0]}
HOST=${args[1]}
LOCAL_DATA_STORE=${args[2]}

scp dsigdo@$HOST:/tmp/eae/$ZipFileName $LOCAL_DATA_STORE