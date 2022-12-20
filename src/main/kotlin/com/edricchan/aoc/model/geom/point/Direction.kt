package com.edricchan.aoc.model.geom.point

enum class Direction(val point: Point) {
    Up(Point(0, 1)),
    Down(Point(0, -1)),
    Left(Point(-1, 0)),
    Right(Point(1, 0));

    operator fun times(by: Int) = point * by
}
