#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail
set -o xtrace

readonly SCRIPT_DIR="$(cd "$(dirname "$0")"; pwd)"
readonly PROJECT_HOME="${SCRIPT_DIR}"/..
#readonly PROJECT_NAME="$(basename "$(cd "${PROJECT_HOME}"; pwd)")"
readonly PROJECT_NAME=demo

cd "${PROJECT_HOME}"

export SPRING_PROFILES_ACTIVE=local
export LOG_LEVEL=DEBUG

export MYSQL_HOST="${MYSQL_HOST:-localhost}"
export MYSQL_DATABASE="${MYSQL_DATABASE:-demo}"
export MYSQL_USER="${MYSQL_USER:-demo}"
export MYSQL_PASSWORD="${MYSQL_PASSWORD:-password}"

main() {
  local sub_command="$1"

  case "${sub_command}" in

    develop)
      ./gradlew \
        clean \
        flywayMigrate \
        generateTablesJooqSchemaSource \
        bootRun
        #-Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,ad    dress=5005
      ;;

    # GRADLE_OPTS、LC_ALL、--no-daemon は OWASP Dependency Check での以下の issue の回避のため
    # https://github.com/jeremylong/DependencyCheck/issues/1742
    build)
      export GRADLE_OPTS="-Dsun.jnu.encoding=UTF-8 -Dfile.encoding=UTF-8" 
      export LC_ALL="C.UTF-8"

      ./gradlew \
        --no-daemon \
        --stacktrace \
        clean \
        dependencyCheckAnalyze \
        flywayMigrate \
        generateTablesJooqSchemaSource \
        build
      ;;

    test)
      ./gradlew \
        clean \
        flywayMigrate \
        generateTablesJooqSchemaSource \
        test
      ;;

    jar)
      java -jar build/libs/"${PROJECT_NAME}"-0.0.1-SNAPSHOT.jar
      ;;

    *)
      err "Unexpected sub command : '${sub_command}'"
      ;;

  esac
}

main "$@"
