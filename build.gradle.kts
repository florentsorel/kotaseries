import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
}

group = "com.betaseries"
version = "0.0.1"

repositories {
    mavenCentral()
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
    sourceSets {
        all {
            languageSettings.optIn("kotlinx.serialization.ExperimentalSerializationApi")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
    implementation("io.ktor:ktor-client-core:2.3.11")
    implementation("io.ktor:ktor-client-cio:2.3.11")
    implementation("io.ktor:ktor-client-cio-jvm:2.3.11")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("ch.qos.logback:logback-classic:1.5.3")

    testImplementation(kotlin("test"))
    testImplementation(platform("io.kotest:kotest-bom:5.9.1"))
    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-assertions-core")
}

tasks.test {
    useJUnitPlatform()
}
