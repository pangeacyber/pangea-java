#!/bin/bash

# This script finds all the  pom.xml  files in the current directory and its subdirectories,
# extracts the directory name, sorts and removes duplicates, and then prints the directory names in a list format.

find . -type f -name "pom.xml" -exec dirname {} \; | sort | uniq | sed 's|^\./||' | sed 's/^/- /'