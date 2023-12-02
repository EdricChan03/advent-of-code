plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "com.edricchan"
version = "1.0-SNAPSHOT"

dependencies {
    // Testing frameworks
    testImplementation(libs.kotlin.test)
    // Kotest
    testImplementation(platform(libs.kotest.bom))
    testImplementation(libs.bundles.kotest)
    // Mockk
    testImplementation(libs.mockk)
}

kotlin {
    jvmToolchain(17)
}

tasks {
    test {
        useJUnitPlatform()
    }
}
