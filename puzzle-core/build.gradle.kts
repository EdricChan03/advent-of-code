plugins {
    com.edricchan.aoc.kotlin
    `java-test-fixtures`
}

dependencies {
    // Kotest
    testFixturesImplementation(libs.kotlin.reflect)
    testFixturesApi(platform(libs.kotest.bom))
    testFixturesApi(libs.bundles.kotest)
    // Mockk
    testImplementation(libs.mockk)
}
