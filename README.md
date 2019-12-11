# kotlin-spring-jooq-flyway

Sample project with Kotlin, Spring Boot, JOOQ, Flyway, etc ...

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
