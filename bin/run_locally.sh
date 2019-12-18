#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail
set -o xtrace

readonly SCRIPT_DIR="$(cd "$(dirname "$0")"; pwd)"
readonly PROJECT_HOME="${SCRIPT_DIR}"/..

cd "${PROJECT_HOME}"

export SPRING_PROFILES_ACTIVE=local
export LOG_LEVEL=DEBUG

export MYSQL_HOST=localhost
export MYSQL_DATABASE=demo
export MYSQL_USER=demo
export MYSQL_PASSWORD=password

err() {
  echo "[$(date +'%Y-%m-%dT%H:%M:%S%z')]: $@" >&2
}

main() {
  local sub_command="$1"

  case "${sub_command}" in

    develop)
      ./gradlew \
        clean \
        flywayMigrate \
        generateTablesJooqSchemaSource \
        bootRun \
      ;;

    build)
      ./gradlew \
        clean \
        flywayMigrate \
        generateTablesJooqSchemaSource \
        build
      ;;

    jar)
      java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
      ;;

    *)
      err "Unexpected sub command : '${sub_command}'"
      ;;

  esac
}

main "$@"
