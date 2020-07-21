#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail
set -o xtrace

readonly SCRIPT_DIR="$(cd "$(dirname "$0")"; pwd)"
readonly PROJECT_HOME="${SCRIPT_DIR}"/..
readonly PROJECT_NAME=demo

docker run \
  --rm \
  -p 8080:8080 \
  -e MYSQL_HOST=mysql \
  --net kotlin-spring-jooq-flyway_default \
  demo:0.0.1-SNAPSHOT
