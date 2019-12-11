# kotlin-spring-jooq-flyway

Sample project with Kotlin, Spring Boot, JOOQ, Flyway, etc ...

## JOOQ によるコードの生成

```bash
$ ./gradlew generateTablesJooqSchemaSource
```

## TODO

### Flyway
* Flyway によるマイグレーションを起動時に実行しないか並列実行の制御
* Flyway によるマイグレーションをデプロイ時に実行

### JOOQ
* ローカル開発時の JOOQ のコード生成の自動化
* 多対一の JOIN に SimpleFlatMapper を使用
  * https://stackoverflow.com/questions/33845239/jooq-single-query-with-one-to-many-relationship
  * https://simpleflatmapper.org/
* 起動時に JOOQ を初期化
* コネクションプールを使わない
* データアクセスの自動テスト

### Swagger
* Spring Fox で Swagger の生成
* API の自動テスト
