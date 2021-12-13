package com.edricchan.aoc.year2021.day05

data class Point2D(val x: Int, val y: Int) {
    operator fun rangeTo(other: Point2D): Line {
        return Line(this, other)
    }
}

fun String.toPoint2D(): Point2D {
    val (x, y) = split(",").map { it.toInt() }
    return Point2D(x, y)
}
