package com.edricchan.aoc.year2021.day05

import kotlin.math.abs

data class Line(val start: Point2D, val end: Point2D) {
    /** Gets all points between [start] and [end]. */
    fun getPoints(): List<Point2D> {
        val points = mutableListOf<Point2D>()

        var (x, y) = start
        val dx = end.x - start.x
        val dy = end.y - start.y

        val steps = abs(dx).coerceAtLeast(abs(dy))
        val xInc = dx / steps
        val yInc = dy / steps

        for (i in 0..steps) {
            points += Point2D(x, y)
            x += xInc
            y += yInc
        }

        return points
    }

    /** Checks whether this line is horizontal. */
    val isHorizontal = start.y == end.y

    /** Checks whether this line is vertical. */
    val isVertical = start.x == end.x

    /** Checks whether this line is non-diagonal. */
    val isNonDiagonal = isHorizontal || isVertical
}
