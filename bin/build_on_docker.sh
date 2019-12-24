#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail
set -o xtrace

readonly APP_SERVICE=app
readonly DB_SERVICE=mysql

readonly SCRIPT_DIR="$(cd "$(dirname "$0")"; pwd)"
readonly PROJECT_HOME="${SCRIPT_DIR}"/..

cd "${PROJECT_HOME}"

#
# MySQL のコンテナが起動完了したかを返します
#
is_mysql_container_ready() {
  local ready_log_count="$(docker-compose logs "${DB_SERVICE}" \
    | grep 'mysqld: ready for connections.' \
    | wc -l)"

  [[ ready_log_count -eq 2 ]]
}

#
# 対象のコンテナでエラーが発生したかを返します
#
error_occurred_in_service() {
  local target_service="$1"

  local error_log_count="$(docker-compose logs "${target_service}" \
    | grep 'ERROR' \
    | wc -l)"

  [[ error_log_count -gt 0 ]]
}

#
# Spring のコンテナの終了コードを取得します
#
get_app_container_exit_code() {
  local container_id="$(docker-compose ps -q "${APP_SERVICE}")"

  docker inspect "${container_id}" \
    | jq '.[0].State.ExitCode'
}

#
# MySQL のコンテナの起動を待ちます
#
wait_for_mysql_container_starting() {
  while true
  do
    if is_mysql_container_ready; then
      echo '=========================='
      echo 'MySQL container is ready !'
      echo '=========================='
      break

    elif error_occurred_in_service "${DB_SERVICE}"; then
      echo '==================================' >&2
      echo 'Error occurred in MySQL container.' >&2
      echo '==================================' >&2
      break

    else
      # 起動中の場合
      echo 'Waiting for container starting...'
      sleep 1
    fi
  done
}

main() {
  docker-compose down

  docker-compose up -d "${DB_SERVICE}"
  wait_for_mysql_container_starting

  SUB_COMMAND=build docker-compose up "${APP_SERVICE}"
  local exit_code="$(get_app_container_exit_code)"

  docker-compose down

  exit "${exit_code}"
}

main "$@"
