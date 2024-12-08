package com.edricchan.aoc.year2024

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.solve

fun main() = solve(block = ::Day04)

private enum class Letter {
    X,
    M,
    A,
    S;

    companion object {
        fun fromChar(char: Char): Letter = when (char) {
            'X' -> X
            'M' -> M
            'A' -> A
            'S' -> S
            else -> throw IllegalArgumentException("Invalid char $char")
        }
    }
}

private fun List<String>.countXmasWordSearch(): Int {
    val grid = map { it.map(Letter.Companion::fromChar) }

    var diag = 0
    for ((rIndex, row) in grid.withIndex()) {
        for ((cIndex, c) in row.withIndex()) {
            if (c != Letter.X) continue

            for (vert in -1..1) {
                for (hori in -1..1) {
                    if (vert == 0 && hori == 0) continue

                    if (grid.getOrNull(rIndex + vert * 1)?.getOrNull(cIndex + hori * 1) == Letter.M
                        && grid.getOrNull(rIndex + vert * 2)?.getOrNull(cIndex + hori * 2) == Letter.A
                        && grid.getOrNull(rIndex + vert * 3)?.getOrNull(cIndex + hori * 3) == Letter.S
                    ) diag++
                }
            }
        }
    }

    return diag
}

private fun List<String>.countMasX(): Int {
    var xCount = 0
    for ((rIndex, row) in withIndex()) {
        if ('A' !in row) continue

        if (rIndex == 0 || rIndex == lastIndex) continue

        """A""".toRegex().findAll(row)
            .map { it.range.first }
            .forEach reg@{
                // Edge-cases
                if (it == 0 || it == row.lastIndex) return@reg

                val top = this[rIndex - 1].run {
                    this[it - 1].toString() + this[it + 1]
                }
                val bottom = this[rIndex + 1].run {
                    this[it - 1].toString() + this[it + 1]
                }
                if (
                    (top == "SS" && bottom == "MM") ||
                    (top == "MM" && bottom == "SS") ||
                    // S . M
                    // . A .
                    // S . M
                    (top == "SM" && bottom == "SM") ||
                    // M . S
                    // . A .
                    // M . S
                    (top == "MS" && bottom == "MS")
                ) xCount++
            }
    }

    return xCount
}

class Day04 : Puzzle<Int, Int>(
    year = 2024, day = 4
) {
    override fun solvePartOne(): Int {
        return input.countXmasWordSearch()
    }

    override fun solvePartTwo(): Int {
        return input.countMasX()
    }
}
