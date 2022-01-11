# kotlin-spring-jooq-flyway

[![CircleCI](https://circleci.com/gh/os1ma/kotlin-spring-jooq-flyway.svg?style=svg)](https://circleci.com/gh/os1ma/kotlin-spring-jooq-flyway)

[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](LICENSE)

Sample project with Kotlin, Spring Boot, JOOQ, Flyway, etc ...

## Dependencies

- Java 8
- Docker
- Docker Compose

## How to run

### Develop with local JDK and MySQL on container

Develop

```bash
$ docker-compose up -d mysql
$ ./bin/run_locally.sh develop
```

Unit testing

```bash
$ docker-compose up -d mysql
$ ./bin/run_locally.sh test
```

Build and run JAR

```bash
$ docker-compose up -d mysql
$ ./bin/run_locally.sh build
$ ./bin/run_locally.sh jar
```

### Develop with JDK on container and MySQL on container

Develop

```bash
$ docker-compose up -d
```

Build

```bash
$ ./bin/build_on_docker.sh
```

## How to call Web API

```bash
$ curl http://localhost:8080/health
$ curl http://localhost:8080/tasks
$ curl http://localhost:8080/tasks/1
$ curl http://localhost:8080/tasks -X POST -H "Content-Type: application/json" -d '{"name": "MyTask", "assigneeUserId": 1}'
```

## About the tools used in this repository

### Flyway

Migration by Flyway will be executed when Spring Boot is started or when the `. /gradlew flywayMigrate` command executed.

Even when migrations are executed in parallel, they are locked properly.

See: [Can multiple nodes migrate in parallel?](https://flywaydb.org/documentation/faq.html#parallel)

### SpringFox

If you start with Profile local, you can get Swagger documentation at the following endpoints.

- JSON document ... http://localhost:8080/v2/api-docs
- Swagger UI ... http://localhost:8080/swagger-ui.html

### OWASP Dependency Check

A vulnerability assessment of dependent libraries is performed at build time.

The build is configured to fail if a CVSS 7.0 or higher vulnerability is found.

### Tevern

Automated API testing tool.
