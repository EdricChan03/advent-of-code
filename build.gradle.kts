import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "com.edricchan"
version = "1.0-SNAPSHOT"

dependencies {
    // Testing frameworks
    testImplementation(kotlin("test"))
    // Kotest
    testImplementation(platform(libs.kotest.bom))
    testImplementation(libs.bundles.kotest)
    // Mockk
    testImplementation(libs.mockk)
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

