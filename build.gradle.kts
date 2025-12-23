plugins {
    java
    checkstyle
    application
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example"
version = "1.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot для REST API
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Swagger/OpenAPI документация
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // Тестирование
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.example.CalculatorApplication")
}

checkstyle {
    toolVersion = "10.12.0"
    configFile = file("$rootDir/config/checkstyle/checkstyle.xml")
    maxWarnings = 0
}