#!/usr/bin/env bash

set -e

cd -- "$(dirname -- "$0")/.."

npx start-server-and-test --expect 404 \
  "npx @stoplight/prism-cli mock -d --json-schema-faker-fillProperties=false src/test/java/cloud/pangeacyber/pangea/testdata/ai-guard.openapi.json" \
  4010 \
  "mvn test -B -e -Dtest=cloud.pangeacyber.pangea.ai_guard.AIGuardTest"

npx start-server-and-test --expect 404 \
  "npx @stoplight/prism-cli mock -d --json-schema-faker-fillProperties=false src/test/java/cloud/pangeacyber/pangea/testdata/prompt-guard.openapi.json" \
  4010 \
  "mvn test -B -e -Dtest=cloud.pangeacyber.pangea.prompt_guard.PromptGuardTest"
