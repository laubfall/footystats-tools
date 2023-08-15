#!/bin/bash

# Definieren Sie die Funktion check_mvn_exit_status
check_exit_status() {
    # Übernehmen Sie den Namen des Kommandos als ersten Parameter
    local command_name="$1"

    # Überprüfen Sie den Exit-Status des Kommandos
    if [ $? -ne 0 ]; then
        # Beenden Sie das Skript, wenn das Kommando fehlgeschlagen ist
        echo "Das $command_name-Kommando ist fehlgeschlagen. Das Skript wird beendet."
        exit 1
    fi
}

# Definieren Sie die Funktion check_mvn_output
check_mvn_output() {
    # Übernehmen Sie das Ergebnis des mvn-Kommandos als ersten Parameter
    local mvn_output="$1"

    # Überprüfen Sie, ob das Ergebnis des mvn-Kommandos einen Fehler enthält
    if [[ $mvn_output == *"BUILD FAILURE"* ]]; then
        # Beenden Sie das Skript, wenn das mvn-Kommando fehlgeschlagen ist
        echo "Das mvn-Kommando ist fehlgeschlagen. Das Skript wird beendet."
        exit 1
    fi
}

finalJarName=footystats-tools-backend.jar

cd ../footystats-tools-backend || exit

echo "Bump backend version"
check_mvn_output "$(mvn versions:set -DnewVersion=$1 -DgenerateBackupPoms=false)"
check_exit_status

echo "Create frontend artifacts that match prod requirements (api url)"
check_mvn_output "$(mvn -DskipTests -Dfrontend.api.generator.backendurl=footystats-tools -P openapi install)"
check_exit_status

echo "Build footystats-tools backend"
check_mvn_output "$(mvn package)"
check_exit_status

cd - || exit
echo "Build footystats-tools frontend"
cd ../footystats-tools-frontend || exit

echo "Bump frontend version"
npm version --allow-same-version $1
check_exit_status
rm -rf dist
npm run-script build
check_exit_status

cd ..
zipName=footystats-release-$1.zip
echo "Build application zip"
cp footystats-tools-backend/target/backend-$1.jar footystats-tools-backend/target/$finalJarName
zip -r $zipName footystats-tools-backend/target/$finalJarName footystats-tools-frontend/dist
