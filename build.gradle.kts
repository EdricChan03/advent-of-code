import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}

