package com.edricchan.aoc.model.geom.point

import com.edricchan.aoc.model.geom.line.Line
import com.edricchan.aoc.model.geom.line.lineTo

data class Point(
    val x: Int,
    val y: Int
) : Comparable<Point> {
    override fun compareTo(other: Point) =
        if (y == other.y) x.compareTo(other.x) else y.compareTo(other.y)

    /** Creates a line from this point to the [other point][other]. */
    operator fun rangeTo(other: Point): Line {
        return this lineTo other
    }

    /** Adds the given [other] to this point. */
    operator fun plus(other: Point) = plus(other.x to other.y)

    /** Adds the given [other] (a pair of coordinates) to this point. */
    operator fun plus(other: Pair<Int, Int>) =
        copy(x = x + other.first, y = y + other.second)

    /** Subtracts the given [other] from this point. */
    operator fun minus(other: Point) = minus(other.x to other.y)

    /** Subtracts the given [other] (a pair of coordinates) from this point. */
    operator fun minus(other: Pair<Int, Int>) =
        copy(x = x - other.first, y = y - other.second)

    /** Multiplies the point's coordinates by [value] times. */
    operator fun times(value: Int) = copy(x = x * value, y = y * value)

    /** Moves this point to the given [other]. */
    infix fun moveTo(other: Point) = other

    /** Moves this point to the given ([x], [y]) point. */
    fun moveTo(x: Int, y: Int) = copy(x = x, y = y)

    /** Moves this point in the specified [dx]/[dy] gradient. */
    fun move(dx: Int, dy: Int) = plus(dx to dy)

    /** Moves this point in the specified [d] (pair of `dx/dy`) gradient. */
    fun move(d: Pair<Int, Int>) = plus(d)

    /** Moves this point in the specified [direction] once. */
    infix fun move(direction: Direction) = plus(direction.point)

    /** Moves this point in the specified [direction], [by] times. */
    fun moveBy(direction: Direction, by: Int) =
        copy(x = x + direction.point.x * by, y = y + direction.point.y * by)

    /** Moves this point up once. */
    fun moveUp() = move(Direction.Up)

    /** Moves this point up [by] times. */
    fun moveUp(by: Int) = moveBy(Direction.Up, by)

    /** Moves this point down once. */
    fun moveDown() = move(Direction.Down)

    /** Moves this point down [by] times. */
    fun moveDown(by: Int) = moveBy(Direction.Down, by)

    /** Moves this point left once. */
    fun moveLeft() = move(Direction.Left)

    /** Moves this point left [by] times. */
    fun moveLeft(by: Int) = moveBy(Direction.Left, by)

    /** Moves this point right once. */
    fun moveRight() = move(Direction.Right)

    /** Moves this point right [by] times. */
    fun moveRight(by: Int) = moveBy(Direction.Right, by)

    /** Retrieves the top neighbouring point. */
    val top by lazy { Point(x, y - 1) }

    /** Retrieves the bottom neighbouring point. */
    val bottom by lazy { Point(x, y + 1) }

    /** Retrieves the left neighbouring point. */
    val left by lazy { Point(x - 1, y) }

    /** Retrieves the right neighbouring point. */
    val right by lazy { Point(x + 1, y) }
}

fun Point.adjacentPoints(): Set<Point> {
    val directions = listOf(
        Point(-1, -1), Point(0, -1), Point(1, -1),
        Point(-1, 0), /* [origin], */ Point(1, 0),
        Point(-1, 1), Point(0, 1), Point(1, 1)
    )
    return directions.map { Point(this.x + it.x, this.y + it.y) }.toSet()
}

/** Creates a [Point] from the given [Pair] of [Int] coordinates. */
fun Pair<Int, Int>.toPoint() = Point(first, second)
