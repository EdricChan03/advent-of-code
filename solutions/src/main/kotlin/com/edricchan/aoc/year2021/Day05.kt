package com.edricchan.aoc.year2021

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.solve
import com.edricchan.aoc.year2021.day05.Board
import com.edricchan.aoc.year2021.day05.Line
import com.edricchan.aoc.year2021.day05.toPoint2D

fun main() = solve { Day05() }

class Day05 : Puzzle<Int, Int>(year = 2021, day = 5) {
    private val vents = input
        .map { entry ->
            entry.split(" -> ").map { it.toPoint2D() }
                .zipWithNext { a, b -> Line(a, b) }
                .first()
        }

    override fun solvePartOne(): Int {
        val nonDiagonalVents = vents.filter { it.isNonDiagonal }
        val board = Board().apply {
            addVents(nonDiagonalVents)
        }

        return board.getPointsWithVents().size
    }

    override fun solvePartTwo(): Int {
        val board = Board().apply {
            addVents(vents)
        }

        return board.getPointsWithVents().size
    }
}
