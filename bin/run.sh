#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail
set -o xtrace

readonly SCRIPT_DIR="$(cd "$(dirname "$0")"; pwd)"
readonly PROJECT_HOME="${SCRIPT_DIR}"/..

cd "${PROJECT_HOME}"

export LOG_LEVEL=DEBUG

export MYSQL_HOST=localhost
export MYSQL_DATABASE=demo
export MYSQL_USER=demo
export MYSQL_PASSWORD=password

java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
