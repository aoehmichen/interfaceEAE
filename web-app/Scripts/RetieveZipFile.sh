#!/usr/bin/env bash

args=("$@")
ZipFileName=${args[0]}

scp -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null

echo "File Retrieved"