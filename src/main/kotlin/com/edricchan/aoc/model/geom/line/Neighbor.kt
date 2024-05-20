package com.edricchan.aoc.model.geom.line

import com.edricchan.aoc.model.geom.point.Point
import com.edricchan.aoc.model.geom.point.adjacentPoints

/** Gets the [Set] of adjacent points to the receiver [Line]. */
fun Line.adjacentPoints(): Set<Point> {
    val pointsOnLine = pointsSequence().toSet()
    val adjacentPoints = pointsOnLine.flatMap(Point::adjacentPoints)

    // Exclude adjacent points that are already part of the line
    return adjacentPoints.filter { it !in pointsOnLine }.toSet()
}
