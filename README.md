# kotlin-spring-jooq-flyway

[![CircleCI](https://circleci.com/gh/os1ma/kotlin-spring-jooq-flyway.svg?style=svg)](https://circleci.com/gh/os1ma/kotlin-spring-jooq-flyway)

[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](LICENSE)

Sample project with Kotlin, Spring Boot, JOOQ, Flyway, etc ...

## 開発手順

### 依存関係
* Java 8
* Docker
* Docker Compose

### ローカルの Java とコンテナ上の MySQL で開発する場合

開発

```bash
$ docker-compose up -d mysql
$ ./bin/run_locally.sh develop
```

Unit テスト

```bash
$ docker-compose up -d mysql
$ ./bin/run_locally.sh test
```

ビルドして JAR を起動

```bash
$ docker-compose up -d mysql
$ ./bin/run_locally.sh build
$ ./bin/run_locally.sh jar
```

### コンテナ上の Java と MySQL で開発する場合

開発

```bash
$ docker-compose up -d
```

ビルド

```bash
$ ./bin/build_on_docker.sh
```

## API 実行例

```bash
$ curl http://localhost:8080/health
$ curl http://localhost:8080/tasks
$ curl http://localhost:8080/tasks/1
$ curl http://localhost:8080/tasks -X POST -H "Content-Type: application/json" -d '{"name": "MyTask", "assigneeUserId": 1}'
```

## 使用ツールについて

### Flyway

Spring Boot の起動または `./gradlew flywayMigrate` コマンドの実行でマイグレーションが実行される。

並列でマイグレーションが実行された場合も適切にロックされる。

参考
* [Can multiple nodes migrate in parallel?](https://flywaydb.org/documentation/faq.html#parallel)

### SpringFox

Profile local で起動すると以下のエンドポイントで Swagger のドキュメントが取得可能。

* JSON ファイル ... http://localhost:8080/v2/api-docs
* Swagger UI ... http://localhost:8080/swagger-ui.html

### OWASP Dependency Check

ビルド時に依存ライブラリの脆弱性診断が実行される。

CVSS 7.0 以上の脆弱性が発見された場合にビルドが失敗するよう設定済み。

### Tevern

API の自動テストツール

## TODO

### JOOQ
* 多対一の JOIN に SimpleFlatMapper を使用
  * https://stackoverflow.com/questions/33845239/jooq-single-query-with-one-to-many-relationship
  * https://simpleflatmapper.org/

### CI
* 開発環境が起動できることのテスト
* ビルド成果物を CircleCI の artifact にアップロード
