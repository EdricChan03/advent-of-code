package com.edricchan.aoc.year2023.day02

data class Cubes(
    val kind: Color,
    val count: Int
) {
    enum class Color(val rawValue: String) {
        Red("red"),
        Green("green"),
        Blue("blue");

        companion object {
            fun fromValue(value: String) = Color.entries.find { it.rawValue == value }
            fun requireValue(value: String) = Color.entries.first { it.rawValue == value }
        }
    }
}

fun String.toCubes(): Cubes {
    val (countRaw, kind) = split(" ")
    return Cubes(Cubes.Color.requireValue(kind), countRaw.toInt())
}
