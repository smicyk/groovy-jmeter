#!/bin/sh

#read -p "Username: " username
#read -p "Password: " -s password
#echo
# Run Command
export MAVEN_USERNAME="smicyk"
export MAVEN_PASSWORD=""

./gradlew clean build publishMavenPublicationToMavenRepository
