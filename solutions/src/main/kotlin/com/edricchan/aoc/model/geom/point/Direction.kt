package com.edricchan.aoc.model.geom.point

enum class Direction(
    val cartesianDiffPoint: Point,
    val indexDiffPoint: Point
) {
    Up(
        Point(0, 1),
        Point(0, -1)
    ),
    Down(
        Point(0, -1),
        Point(0, 1)
    ),
    Left(
        Point(-1, 0),
        Point(-1, 0)
    ),
    Right(
        Point(1, 0),
        Point(1, 0)
    );

    operator fun times(by: Int) = cartesianDiffPoint * by

    /** Gets the reflected version of this direction. */
    val reflectedDirection: Direction by lazy {
        when (this) {
            Up -> Down
            Down -> Up
            Left -> Right
            Right -> Left
        }
    }

    /** Gets the 90° clockwise version of this direction.  */
    fun rotateClockwise90(): Direction = when(this) {
        Up -> Right
        Down -> Left
        Left -> Up
        Right -> Down
    }
}
