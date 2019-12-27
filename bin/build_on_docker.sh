#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail
set -o xtrace

readonly APP_SERVICE=app
readonly DB_SERVICE=mysql
readonly TEST_SERVICE=tavern

readonly SCRIPT_DIR="$(cd "$(dirname "$0")"; pwd)"
readonly PROJECT_HOME="${SCRIPT_DIR}"/..
readonly TEST_PROJECT_HOME="${PROJECT_HOME}"/tavern

#
# 指定したサービスの指定したログの出現回数を取得します
#
get_log_count_in_service() {
  local target_service="$1"
  local grep_condition="$2"

  docker-compose logs "${target_service}" \
    | grep "${grep_condition}" \
    | wc -l
}

#
# MySQL のコンテナが起動完了したかを返します
#
is_mysql_container_ready() {
  local ready_log_count="$(get_log_count_in_service "${DB_SERVICE}" 'mysqld: ready for connections.')"
  [[ ready_log_count -eq 2 ]]
}

#
# Spring のコンテナが起動完了したかを返します
#
is_app_container_ready() {
  local ready_log_count="$(get_log_count_in_service "${APP_SERVICE}" 'Started')"
  [[ ready_log_count -eq 1 ]]
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
# 指定したコンテナの終了コードを取得します
#
get_container_exit_code() {
  local target_service="$1"
  local container_id="$(docker-compose ps -q "${target_service}")"

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
      echo '========================'
      echo 'The container is ready !'
      echo '========================'
      break

    elif error_occurred_in_service "${DB_SERVICE}"; then
      echo '================================' >&2
      echo 'Error occurred in the container.' >&2
      echo '================================' >&2
      break

    else
      # 起動中の場合
      echo 'Waiting for container starting...'
      sleep 1
    fi
  done
}

#
# JAR ファイル実行コンテナの起動を待ちます
#
wait_for_jar_container_starting() {
  while true
  do
    if is_app_container_ready; then
      echo '========================'
      echo 'The container is ready !'
      echo '========================'
      break

    elif error_occurred_in_service "${APP_SERVICE}"; then
      echo '================================' >&2
      echo 'Error occurred in the container.' >&2
      echo '================================' >&2
      break

    else
      # 起動中の場合
      echo 'Waiting for container starting...'
      sleep 1
    fi
  done
}

main() {
  cd "${PROJECT_HOME}"

  # クリーンアップ
  docker-compose down

  # MySQL 起動
  docker-compose up -d "${DB_SERVICE}"
  wait_for_mysql_container_starting

  # JAR ファイルのビルド
  SUB_COMMAND=build docker-compose up "${APP_SERVICE}"
  local build_exit_code="$(get_container_exit_code "${APP_SERVICE}")"

  # JAR の起動
  SUB_COMMAND=jar docker-compose up -d "${APP_SERVICE}"
  wait_for_jar_container_starting

  # Tavern による API テスト
  cd "${TEST_PROJECT_HOME}"
  docker-compose down
  docker-compose up
  local test_exit_code="$(get_container_exit_code "${TEST_SERVICE}")"
  docker-compose down

  # クリーンアップ
  cd "${PROJECT_HOME}"
  docker-compose down

  exit "$((${build_exit_code}+${test_exit_code}))"
}

main "$@"
