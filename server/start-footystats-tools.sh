#!/bin/bash
echo "Starting backend"
java -jar footystats-tools-release.jar

echo "Starting frontend"
npx serve -l 3000
