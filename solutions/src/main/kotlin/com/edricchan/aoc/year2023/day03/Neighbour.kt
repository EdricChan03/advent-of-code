package com.edricchan.aoc.year2023.day03

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun getLineDistance(
    x: Double, y: Double, a: Double, b: Double, c: Double
): Double {
    return abs(a * x + b * y + c) / sqrt(a.pow(2) + b.pow(2))
}

fun isPointAwayFromLine(
    x: Double, y: Double, a: Double, b: Double, c: Double, threshold: Int
): Boolean {
    val distance = getLineDistance(x, y, a, b, c)
    return distance > threshold
}
