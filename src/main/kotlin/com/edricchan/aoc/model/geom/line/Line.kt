package com.edricchan.aoc.model.geom.line

import com.edricchan.aoc.model.geom.point.Point

data class Line(
    val start: Point,
    val end: Point
) {
    /** Converts this line to a [Pair]. */
    fun toPair() = start to end

    /** Generates all possible points in this line as a [Sequence]. */
    // TODO
    fun pointsSequence(): Sequence<Point> = TODO()
}

/** Checks if the receiver point intersects with the [line]. */
infix fun Point.intersects(line: Line) = x in line.start.x..line.end.x && y in line.start.y..line.end.y

/** Creates a [Line] from the specified pair. */
fun Pair<Point, Point>.toLine() = Line(first, second)

/** Create a [Line] from the specified points. */
infix fun Point.lineTo(other: Point) = Line(this, other)
