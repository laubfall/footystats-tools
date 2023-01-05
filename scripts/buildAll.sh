#!/bin/bash
cd ../footystats-tools-backend || exit
mvn install -P openapi
cd -
cd ../footystats-tools-frontend || exit
yarn run openapi-generator-cli generate -g typescript-fetch -i ../footystats-tools-backend/target/openapi/openapi.json -o src/footystats-frontendapi --additional-properties=typescriptThreePlus=true --server-variables=env=ubuntu,port=3000
