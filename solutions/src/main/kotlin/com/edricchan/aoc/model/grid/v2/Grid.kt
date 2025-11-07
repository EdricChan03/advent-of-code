package com.edricchan.aoc.model.grid.v2

import com.edricchan.aoc.model.geom.point.Point

/** Marker interface to represent a single point in a [Grid1D]. */
interface GridPoint {
    val point: Point
}

interface Grid {
    val width: Int
    val height: Int
}

data class Grid1D<Cell : GridPoint>(
    val rawList: List<Cell>,
    override val width: Int
) : Grid {
    override val height: Int by lazy { if (rawList.isEmpty()) 0 else (rawList.size / width) }

    operator fun get(x: Int, y: Int): Cell = rawList[y * width + x]

    operator fun get(point: Point) = this[point.x, point.y]

    fun getOrNull(x: Int, y: Int): Cell? =
        if (x in 0 until width && y in 0 until height) rawList[y * width + x] else null

    fun getOrNull(point: Point): Cell? = getOrNull(point.x, point.y)

    companion object {
        fun <Cell : GridPoint> fromInput(
            input: List<String>,
            cellTransform: (point: Point, value: Char) -> Cell
        ): Grid1D<Cell> = Grid1D(rawList = input.flatMapIndexed { cIndex, line ->
            line.toList().mapIndexed { rIndex, c ->
                cellTransform(Point(rIndex, cIndex), c)
            }
        }, width = input.first().length)
    }
}

data class MutableGrid1D<Cell : GridPoint>(
    val rawList: MutableList<Cell>,
    override val width: Int
) : Grid {
    override val height: Int by lazy { if (rawList.isEmpty()) 0 else (rawList.size / width) }

    operator fun get(x: Int, y: Int): Cell = rawList[y * width + x]

    operator fun get(point: Point) = this[point.x, point.y]

    fun getOrNull(x: Int, y: Int): Cell? =
        if (x in 0 until width && y in 0 until height) rawList[y * width + x] else null

    fun getOrNull(point: Point): Cell? = getOrNull(point.x, point.y)

    operator fun set(x: Int, y: Int, value: Cell) {
        rawList[y * width + x] = value
    }

    operator fun set(point: Point, value: Cell) {
        set(point.x, point.y, value)
    }

    inline fun update(x: Int, y: Int, updateFn: (Cell) -> Cell) {
        set(x = x, y = y, value = updateFn(get(x = x, y = y)))
    }

    inline fun update(point: Point, updateFn: (Cell) -> Cell) {
        set(point = point, value = updateFn(get(point = point)))
    }
}

fun <Cell : GridPoint> Grid1D<Cell>.toMutableGrid1D(): MutableGrid1D<Cell> = MutableGrid1D(
    rawList = rawList.toMutableList(),
    width = width
)
