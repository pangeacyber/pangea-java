#!/usr/bin/env bash

set -eo pipefail

# Root directory
root_directory=$(pwd)

# Iterate over subdirectories
for directory in "$root_directory"/**/*/; do
    # Check if the directory contains a pom.xml file
    if [ -f "$directory/pom.xml" ]; then
        echo -e "\n\n--------------------------------------------------------------\nEntering directory: $directory"

        # Move to the directory and run Maven commands
        cd "$directory" || exit
        mvn clean && mvn compile && mvn exec:java -Dexec.mainClass=cloud.pangeacyber.examples.App

        echo -e "\nFinish directory: $directory\n--------------------------------------------------------------\n"
        # Move back to the root directory
        cd "$root_directory" || exit
    fi
done
