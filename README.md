# kotlin-spring-jooq-flyway

Sample project with Kotlin, Spring Boot, JOOQ, Flyway, etc ...

## 開発手順

### ローカルの Java を使う場合

開発

```bash
$ docker-compose up -d mysql
$ ./bin/run_locally.sh develop
```

ビルドして JAR で起動

```bash
$ docker-compose up -d mysql
$ ./bin/run_locally.sh build
$ ./bin/run_locally.sh jar
```

### Docker 上の Java を使う場合

開発

```bash
$ docker-compose up -d
```

ビルド

```bash
$ GRADLE_COMMAND=build docker-compose up
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

## TODO

### JOOQ
* 多対一の JOIN に SimpleFlatMapper を使用
  * https://stackoverflow.com/questions/33845239/jooq-single-query-with-one-to-many-relationship
  * https://simpleflatmapper.org/

### Swagger
* Spring Fox で Swagger の生成
* API の自動テスト

### CI
* CI でテスト用のコンテナを起動・接続
