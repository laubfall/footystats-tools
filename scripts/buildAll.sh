#!/bin/bash

finalJarName=footystats-tools-backend.jar

cd ../footystats-tools-backend || exit

echo "Bump backend version"
mvn versions:set -DnewVersion=$1 -DgenerateBackupPoms=false

echo "Create frontend artifacts that match prod requirements (api url)"
mvn -DskipTests -Dfrontend.api.generator.backendurl=footystats-tools -P openapi install

echo "Build footystats-tools backend"
mvn package

cd - || exit
echo "Build footystats-tools frontend"
cd ../footystats-tools-frontend || exit

echo "Bump frontend version"
npm version --allow-same-version $1
rm -rf dist
npm run-script build

cd ..
zipName=footystats-release-$1.zip
echo "Build application zip"
cp footystats-tools-backend/target/backend-$1.jar footystats-tools-backend/target/$finalJarName
zip -r $zipName footystats-tools-backend/target/$finalJarName footystats-tools-frontend/dist
