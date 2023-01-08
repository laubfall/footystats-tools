#!/bin/bash

ps -A | grep -w java | awk '{print $1}' | xargs kill
ps -A | grep -w node | awk '{print $1}' | xargs kill

rm -rf footystats-tools-backend
rm -rf footystats-tools-frontend

unzip footystats-tools-release.zip
