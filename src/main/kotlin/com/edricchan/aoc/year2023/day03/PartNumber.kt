package com.edricchan.aoc.year2023.day03

import com.edricchan.aoc.model.geom.line.Line
import com.edricchan.aoc.model.geom.point.Point


data class PartNumber(
    val number: Int,
    val bounds: Line
) {
    constructor(
        number: Int,
        startPosX: Int,
        startPosY: Int,
        endPosX: Int,
        endPosY: Int,
    ) : this(
        number = number,
        bounds = Point(startPosX, startPosY)..Point(endPosX, endPosY)
    )

    val startPos by lazy { bounds.start }
    val endPos by lazy { bounds.end }
}

private val partNumRegex = Regex("""\d+""")

fun String.findAllPartNumbers() = partNumRegex.findAll(this)
