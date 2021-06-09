import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ossIndexUsername = System.getenv("OSS_INDEX_USERNAME")
val ossIndexPassword = System.getenv("OSS_INDEX_PASSWORD")

plugins {
    id("org.springframework.boot") version "2.5.0"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("org.owasp.dependencycheck") version "6.1.6"
    kotlin("jvm") version "1.5.0"
    kotlin("plugin.spring") version "1.5.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Database
    runtimeOnly("mysql:mysql-connector-java")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.flywaydb:flyway-core")

    // Document
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")

    // Development
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

dependencyCheck {
    failBuildOnCVSS = 7.0F

    suppressionFile = "etc/java/owasp-suppressions.xml"

    // settings to ignore .NET dependency check
    //
    // see
    // https://jeremylong.github.io/DependencyCheck/dependency-check-gradle/configuration.html
    // https://qiita.com/wrongwrong/items/ef93471c6c22c2202f19
    analyzers.nuspecEnabled = false
    analyzers.nugetconfEnabled = false
    analyzers.assemblyEnabled = false

    analyzers.ossIndex.username = ossIndexUsername
    analyzers.ossIndex.password = ossIndexPassword
}

apply(from = "etc/java/jooq-flyway.gradle")
