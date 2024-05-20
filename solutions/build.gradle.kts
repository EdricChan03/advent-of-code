plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "com.edricchan"
version = "1.0-SNAPSHOT"

dependencies {
    api(projects.puzzleCore)
    // Testing frameworks
    testImplementation(libs.kotlin.test)
    testImplementation(testFixtures(projects.puzzleCore))
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
