import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
}

group = "com.edricchan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Testing frameworks
    testImplementation(kotlin("test"))
    // Kotest
    testImplementation(platform("io.kotest:kotest-bom:5.0.1"))
    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-property")
    testImplementation("io.kotest:kotest-framework-datatest")
    // Mockk
    testImplementation("io.mockk:mockk:1.12.1")
}

java.toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
    test {
        useJUnitPlatform()
    }

    wrapper {
        gradleVersion = "7.5.1"
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}

