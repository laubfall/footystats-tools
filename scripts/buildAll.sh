#!/bin/bash

${1:="release.zip"}
finalJarName=footystats-tools-backend.jar

echo "Build footystats-tools backend"
cd ../footystats-tools-backend || exit
mvn install -P openapi
cd - || exit
echo "Build footystats-tools frontend"
cd ../footystats-tools-frontend || exit
yarn run openapi-generator-cli generate -g typescript-fetch -i ../footystats-tools-backend/target/openapi/openapi.json -o src/footystats-frontendapi --additional-properties=typescriptThreePlus=true --server-variables=env=footystats-tools,port=8080
npm run-script build

cd ..
zipName=$1
echo "Build application zip"
cp footystats-tools-backend/target/backend-0.0.1-SNAPSHOT.jar footystats-tools-backend/target/$finalJarName
zip -r $zipName footystats-tools-backend/target/$finalJarName footystats-tools-frontend/dist
