#!/usr/bin/env bash

set -e

if [ $# -lt 1 ]; then
    echo "usage: validate_tag.sh <git_tag>"
    exit 1
fi

GIT_TAG=$1

if [[ ! $GIT_TAG == "v"* ]]; then
    echo "Git tag must begin with a 'v'."
    exit 1
fi

# Trim the 'v'.
GIT_TAG="${GIT_TAG:1}"

# Move to repo root.
PARENT_PATH=$(cd "$(dirname "${BASH_SOURCE[0]}")"; pwd -P)
pushd "$PARENT_PATH/.."

POM_VERSION=$(grep -Eo "<version>.+<\/version>" packages/pangea-sdk/pom.xml | head -1)

if [[ ! "$POM_VERSION" == *"$GIT_TAG"* ]]; then
    echo "Git tag version '$GIT_TAG' does not match csproj file version '$POM_VERSION'."
    exit 1
fi

VERSION_CLASS=$(grep -Eo "public static final String VERSION = ".+";" packages/pangea-sdk/src/main/java/cloud/pangeacyber/pangea/Version.java)

if [[ ! "$VERSION_CLASS" == *"$GIT_TAG"* ]]; then
    echo "Git tag version '$GIT_TAG' does not match Version class version '$VERSION_CLASS'."
    exit 1
fi

popd
