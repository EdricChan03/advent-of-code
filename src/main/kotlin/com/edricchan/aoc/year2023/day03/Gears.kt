package com.edricchan.aoc.year2023.day03

import com.edricchan.aoc.model.geom.point.Point

private val gearRegex = Regex("""\*""")

fun String.findAllGears() = gearRegex.findAll(this)

fun Point.getIntersectingPartNumbers(numbers: List<PartNumber>): List<PartNumber> {
    return numbers.filter { this in it.bounds }
}
