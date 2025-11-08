plugins {
    java
    checkstyle
    application
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
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.example.Calculator")
}

checkstyle {
    toolVersion.set("10.12.0")
    configFile.set(file("config/checkstyle/checkstyle.xml"))
    maxWarnings.set(0)
}