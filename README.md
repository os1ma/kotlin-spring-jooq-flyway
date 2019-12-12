# kotlin-spring-jooq-flyway

Sample project with Kotlin, Spring Boot, JOOQ, Flyway, etc ...

## 開発手順

### ローカルの Java で開発する場合

```bash
$ docker-compose up -d mysql
$ ./bin/run_locally.sh develop
```

### Docker 上の Java で開発する場合

```bash
$ docker-compose up -d
```

### ローカルの Java でビルドして JAR で動かす場合

```bash
$ docker-compose up -d mysql
$ ./bin/run_locally.sh build
$ ./bin/run_locally.sh jar
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
* データアクセスの自動テスト

### Swagger
* Spring Fox で Swagger の生成
* API の自動テスト

### CI
* ビルド用の docker-compose.yaml を記述
* CI でテスト用のコンテナを起動・接続
