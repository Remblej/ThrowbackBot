plugins {
    kotlin("jvm") version "1.9.23"
    application
}

group = "com.softwareart"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("com.softwareart.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("dev.kord:kord-core:0.14.0")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
    implementation("org.slf4j:slf4j-simple:2.0.13")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}