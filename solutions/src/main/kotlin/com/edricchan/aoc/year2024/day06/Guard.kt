package com.edricchan.aoc.year2024.day06

import com.edricchan.aoc.model.geom.point.Direction
import com.edricchan.aoc.model.geom.point.Point

data class Guard(
    val direction: Direction,
    val point: Point,
    val visitedPoints: Set<Pair<Point, Direction>>
) {
    fun withNewPoint(newPoint: (Guard) -> Point): Guard = copy(point = newPoint(this))

    fun addVisitedPoint(point: Point, direction: Direction): Guard =
        copy(visitedPoints = visitedPoints + (point to direction))

    fun addVisitedPoint(point: (Guard) -> Pair<Point, Direction>): Guard =
        copy(visitedPoints = visitedPoints + point(this))

    fun addVisitedPoints(points: (Guard) -> Set<Pair<Point, Direction>>): Guard =
        copy(visitedPoints = visitedPoints + points(this))

    fun rotated(): Guard = copy(direction = direction.rotateClockwise90())

    /**
     * Steps through the guard's patrolling, returning the new position instead of the [Guard] class.
     *
     * No checking is done if there is an obstruction.
     */
    fun stepPoint(): Point = point moveIndex direction

    companion object {
        fun initial(
            start: Point,
            visitedPoints: Set<Pair<Point, Direction>> = setOf(start to Direction.Up)
        ): Guard = Guard(
            direction = Direction.Up,
            point = start,
            visitedPoints = visitedPoints
        )
    }
}
