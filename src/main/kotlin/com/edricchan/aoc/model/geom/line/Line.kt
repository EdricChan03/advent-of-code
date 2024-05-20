package com.edricchan.aoc.model.geom.line

import com.edricchan.aoc.model.geom.point.Point
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

data class Line(
    val start: Point,
    val end: Point
) {
    /** Converts this line to a [Pair]. */
    fun toPair() = start to end

}

/** Generates a [Sequence] of [Point]s from the receiver [Line]. */
fun Line.pointsSequence(): Sequence<Point> {
    // Handle the special case when the line is only 1 unit
    if (start == end) return sequenceOf(start)

    val dx = end.x - start.x
    val dy = end.y - start.y
    val steps = maxOf(dx.absoluteValue, dy.absoluteValue)
    val xIncrement = dx.toFloat() / steps
    val yIncrement = dy.toFloat() / steps

    return generateSequence(0) { it + 1 }
        .take(steps + 1)
        .map { step ->
            Point(
                (start.x + step * xIncrement).roundToInt(),
                (start.y + step * yIncrement).roundToInt()
            )
        }
}

/** Checks if the receiver point intersects with the [line]. */
infix fun Point.intersects(line: Line) = x in line.start.x..line.end.x && y in line.start.y..line.end.y

/** Creates a [Line] from the specified pair. */
fun Pair<Point, Point>.toLine() = Line(first, second)

/** Create a [Line] from the specified points. */
infix fun Point.lineTo(other: Point) = Line(this, other)
