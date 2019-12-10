# kotlin-spring-jooq-flyway

Sample project with Kotlin, Spring Boot, JOOQ, Flyway, etc ...

## JOOQ によるコードの生成

```bash
$ ./gradlew generateTablesJooqSchemaSource
```

## TODO

* Flyway によるマイグレーション実行の制御
* ローカル開発時の JOOQ のコード生成の自動化
* JOIN を含む SELECT の実行
