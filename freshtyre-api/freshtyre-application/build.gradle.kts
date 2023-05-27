plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.noarg")
    kotlin("plugin.jpa")
    kotlin("plugin.serialization")
    id("org.springframework.boot")
}

val springdocVersion = "1.5.2"

dependencies {
    implementation(project(":freshtyre-api:freshtyre-core"))
    implementation(project(":freshtyre-api:freshtyre-persistance"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.springdoc:springdoc-openapi-ui:$springdocVersion")
    implementation("org.springdoc:springdoc-openapi-data-rest:$springdocVersion")
    implementation("org.springdoc:springdoc-openapi-kotlin:$springdocVersion")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.telegram:telegrambots:6.3.0")
    implementation("org.telegram:telegrambots-spring-boot-starter:6.3.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}