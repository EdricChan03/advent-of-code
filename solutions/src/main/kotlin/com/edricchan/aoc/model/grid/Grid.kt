package com.edricchan.aoc.model.grid

import com.edricchan.aoc.model.geom.point.Point

typealias Grid<T> = List<List<T>>

typealias MutableGrid<T> = MutableList<MutableList<T>>

// Variant which uses a single list
data class Grid1D<Cell>(
    val rawGrid: List<Cell>
) : List<Cell> by rawGrid {
    operator fun get(x: Int, y: Int): Cell = this[(x * y) + x]

    operator fun get(point: Point) = this[point.x, point.y]

    companion object {
        fun <Cell> fromInput(
            input: List<String>,
            cellTransform: (point: Point, value: Char) -> Cell
        ): Grid1D<Cell> = Grid1D(input.flatMapIndexed { cIndex, line ->
            line.toList().mapIndexed { rIndex, c ->
                cellTransform(Point(rIndex, cIndex), c)
            }
        })
    }
}

data class MutableGrid1D<Cell>(
    val rawGrid: MutableList<Cell>
) : MutableList<Cell> by rawGrid {
    operator fun get(x: Int, y: Int): Cell = this[(x * y) + x]

    operator fun get(point: Point) = this[point.x, point.y]

    operator fun set(x: Int, y: Int, value: Cell) {
        this[(x * y) + x] = value
    }

    operator fun set(point: Point, value: Cell) {
        this[point.x, point.y] = value
    }

    companion object {
        fun <Cell> fromInput(
            input: List<String>,
            cellTransform: (point: Point, value: Char) -> Cell
        ): MutableGrid1D<Cell> = MutableGrid1D(input.flatMapIndexed { cIndex, line ->
            line.toList().mapIndexed { rIndex, c ->
                cellTransform(Point(rIndex, cIndex), c)
            }
        }.toMutableList())
    }
}

fun <Cell> Grid1D<Cell>.toMutableGrid1D() = MutableGrid1D<Cell>(
    rawGrid.toMutableList()
)
