#!/bin/bash
# Expects three parameters
# 1: Username to logon to the server
# 2: Servername
# 3: Path to copy footystats

zipName="footystats-tools-release.zip"
if test -f nonexistent_file; then
  rm nonexistent_file
fi

source buildAll.sh $zipName

# Check the exit status of the script
if [ $? -eq 0 ]; then
  echo "Footystats artifacts were build succesfully"
else
  echo "Build all footystats artifacts failed."
  exit 1;
fi

echo "Uploading to server"
scp $zipName $1@$2:$3
