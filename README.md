# kotlin-spring-jooq-flyway

[![CircleCI](https://circleci.com/gh/os1ma/kotlin-spring-jooq-flyway.svg?style=svg)](https://circleci.com/gh/os1ma/kotlin-spring-jooq-flyway)

[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](LICENSE)

Sample project with Kotlin, Spring Boot, JOOQ, Flyway, etc ...

## 開発手順

### 依存ツール
* Java 8
* Docker
* Docker Compose

### ローカルの Java とコンテナ上の MySQL で開発する場合

開発

```bash
$ docker-compose up -d mysql
$ ./bin/run_locally.sh develop
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

## API 利用例

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

Profile local で起動すると以下のエンドポイントに Swagger の JSON が生成される。

http://localhost:8080/v2/api-docs

## TODO

### JOOQ
* 多対一の JOIN に SimpleFlatMapper を使用
  * https://stackoverflow.com/questions/33845239/jooq-single-query-with-one-to-many-relationship
  * https://simpleflatmapper.org/

### Swagger
* API の自動テスト

### Test
* 開発環境が起動できることのテスト
