plugins {
    kotlin("jvm") version "2.1.0"
    application
}

group = "com.aznos"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

}

application {
    mainClass = "com.aznos.MainKt"
}

kotlin {
    jvmToolchain(21)
}