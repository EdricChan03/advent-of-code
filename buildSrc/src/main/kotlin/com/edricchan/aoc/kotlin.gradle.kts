package com.edricchan.aoc

plugins {
    org.jetbrains.kotlin.jvm
}

group = "com.edricchan.aoc"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(21)
}

tasks {
    test {
        useJUnitPlatform()
    }
}

val libs = versionCatalogs.named("libs")

dependencies {
    // Testing frameworks
    testImplementation(libs.findLibrary("kotlin-test").get())
    // Kotest
    testImplementation(platform(libs.findLibrary("kotest-bom").get()))
    testImplementation(libs.findBundle("kotest").get())
}
