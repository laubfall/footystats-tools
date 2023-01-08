#!/bin/bash
echo "Starting backend"

cd footystats-tools-backend/target || exit
nohup /home/ubuntu/.sdkman/candidates/java/current/bin/java -jar footystats-tools-backend.jar --spring.profiles.active=prod &

cd -
cd footystats-tools-frontend/dist || exit

echo "Starting frontend"
nohup npx serve -l 3000 &
