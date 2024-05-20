package com.edricchan.aoc.year2022.day08

import com.edricchan.aoc.model.geom.point.Point
import com.edricchan.aoc.model.geom.point.toPoint
import com.edricchan.aoc.utils.takeUntil

data class HeightPoint(
    val point: Point,
    val height: Int
) : Comparable<HeightPoint> {
    constructor(point: Pair<Int, Int>, height: Int) : this(point.toPoint(), height)

    override fun compareTo(other: HeightPoint) = height.compareTo(other.height)

    /** The x coordinate of this point. */
    val x by point::x

    /** The y coordinate of this point. */
    val y by point::y

    /** Retrieves the top neighbouring point from the [map]. */
    fun getTop(map: HeightMap) = map.getOrNull(x)?.getOrNull(y - 1)

    /** Retrieves the bottom neighbouring point from the [map]. */
    fun getBottom(map: HeightMap) = map.getOrNull(x)?.getOrNull(y + 1)

    /** Retrieves the left neighbouring point from the [map]. */
    fun getLeft(map: HeightMap) = map.getOrNull(x - 1)?.getOrNull(y)

    /** Retrieves the right neighbouring point from the [map]. */
    fun getRight(map: HeightMap) = map.getOrNull(x + 1)?.getOrNull(y)
}

enum class VisibleResult {
    Edge,
    Left,
    Right,
    Top,
    Bottom
}

data class Visible(
    val point: HeightPoint,
    val result: Map<VisibleResult, Boolean>
)

data class HeightMap(
    val grid: List<List<HeightPoint>>
) : List<List<HeightPoint>> by grid {
    init {
        require(grid.size == grid[0].size) {
            "2D list passed in is not a grid, got ${grid.size} x ${grid[0].size}"
        }
    }

    val rowSize by lazy { grid.size }
    val colSize by lazy { grid[0].size }

    val rowLastIndex by lazy { lastIndex }
    val colLastIndex by lazy { grid[0].lastIndex }

    fun asPrettyGrid() = buildString {
        grid.forEach { line ->
            appendLine(line.joinToString(" | ") { "(%02d, %02d)".format(it.point.x, it.point.y) })
            appendLine(line.joinToString(" | ") { "   %02d   ".format(it.height) })
            appendLine("-".repeat(line.size * 11 - 3))
        }
    }

    operator fun get(point: Point) = grid[point.y][point.x]

    private fun getViewablePredidcate(point: Point): (HeightPoint) -> Boolean = { it.height < this[point].height }

    fun horizPoints(point: Point) = this[point.y]

    fun leftPoints(point: Point) = horizPoints(point).filter { it.point < point }
    fun rightPoints(point: Point) = horizPoints(point).filter { it.point > point }

    fun viewableLeftPoints(point: Point) = leftPoints(point)
        .reversed().takeUntil(getViewablePredidcate(point))

    fun viewableRightPoints(point: Point) = rightPoints(point)
        .takeUntil(getViewablePredidcate(point))

    fun vertPoints(point: Point) = map { it[point.x] }

    fun topPoints(point: Point) = vertPoints(point).filter { it.point < point }
    fun bottomPoints(point: Point) = vertPoints(point).filter { it.point > point }

    fun viewableTopPoints(point: Point) = topPoints(point)
        .reversed().takeUntil(getViewablePredidcate(point))
    fun viewableBottomPoints(point: Point) = bottomPoints(point)
        .takeUntil(getViewablePredidcate(point))

    fun isEdgePoint(point: Point) = point.x <= 0 || point.y <= 0 || point.x >= rowLastIndex || point.y >= colLastIndex

    fun isPointVisible(
        point: Point, heightPoints: List<HeightPoint>
    ) =
        heightPoints.all { it.height < this[point].height }

    fun isPointVisible(point: Point) =
        isEdgePoint(point) || (
                isPointVisible(point, leftPoints(point))
                        || isPointVisible(point, rightPoints(point))
                        || isPointVisible(point, topPoints(point))
                        || isPointVisible(point, bottomPoints(point)))

    // If a tree is right on the edge, at least one of its viewing distances will be zero
    fun getPointScore(point: Point) = if (isEdgePoint(point)) 0 else
        viewableTopPoints(point).size *
                viewableBottomPoints(point).size *
                viewableLeftPoints(point).size *
                viewableRightPoints(point).size
}

fun List<List<HeightPoint>>.toHeightMap() = HeightMap(this)
