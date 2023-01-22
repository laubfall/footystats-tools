#!/bin/bash

docker compose up -d

echo 'Wait for 5 seconds to make sure that the mongo container is up and running'
sleep 5
docker compose exec footystats-mongodb sh -c 'mongo -u root -p hello /replica_set_init.js'
