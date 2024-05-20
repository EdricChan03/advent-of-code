package com.edricchan.aoc.year2023.day03

import com.edricchan.aoc.model.geom.point.Point

private val symbolRegex = Regex("""[^\d.]""")

fun String.isSymbol() = symbolRegex matches this

fun String.findAllSymbols() = symbolRegex.findAll(this)

data class Symbol(
    val char: String,
    val position: Point
) {
    constructor(
        char: String,
        posX: Int,
        posY: Int
    ) : this(char = char, position = Point(posX, posY))
}
