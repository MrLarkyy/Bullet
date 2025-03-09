plugins {
    kotlin("jvm") version "2.1.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.12.1")
    implementation("dev.dewy:nbt:1.5.1")
    implementation("org.apache.logging.log4j:log4j-core:2.24.3")
    implementation("com.palantir.javapoet:javapoet:0.6.0")
}

application {
    mainClass = "com.aznos.MainKt"
}

kotlin {
    jvmToolchain(21)
}