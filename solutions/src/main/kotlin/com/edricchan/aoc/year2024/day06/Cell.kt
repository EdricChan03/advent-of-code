package com.edricchan.aoc.year2024.day06

import com.edricchan.aoc.model.geom.point.Point
import com.edricchan.aoc.model.grid.v2.GridPoint

enum class CellType(
    val char: Char
) {
    Empty('.'),
    Obstruction('#'),
    Guard('^');

    companion object {
        fun fromChar(char: Char): CellType = CellType.entries.first { it.char == char }
    }
}

data class Cell(
    override val point: Point,
    val type: CellType
) : GridPoint
