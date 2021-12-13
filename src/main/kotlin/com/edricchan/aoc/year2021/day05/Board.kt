package com.edricchan.aoc.year2021.day05

data class Board(
    val rows: Int = 1000,
    val cols: Int = 1000
) {
    // The board is represented as a 2D array of integers (count of intersecting vents)
    private val board = MutableList(rows) { MutableList(cols) { 0 } }

    /** Adds the specified [vent] to the board. */
    fun addVent(vent: Line) {
        vent.getPoints().forEach {
            board[it.x][it.y]++
        }
    }

    /** Adds the specified [list of vents][vents] to the board. */
    fun addVents(vents: List<Line>) {
        vents.forEach { addVent(it) }
    }

    fun getPoints(): List<List<Pair<Int, Point2D>>> {
        return board.mapIndexed { r, row -> row.mapIndexed { c, i -> i to Point2D(r, c) } }
    }

    fun getPointsWithVents(minVents: Int = 2): List<Pair<Int, Point2D>> {
        return getPoints().flatten().filter { it.first >= minVents }
    }
}
