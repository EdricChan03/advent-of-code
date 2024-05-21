plugins {
    com.edricchan.aoc.kotlin
}

dependencies {
    api(projects.puzzleCore)
    // Testing frameworks
    testImplementation(testFixtures(projects.puzzleCore))
    // Mockk
    testImplementation(libs.mockk)
}
