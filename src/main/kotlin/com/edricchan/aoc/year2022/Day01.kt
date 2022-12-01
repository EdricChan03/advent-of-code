package com.edricchan.aoc.year2022

import com.edricchan.aoc.Puzzle
import com.edricchan.aoc.solve

fun main() = solve { Day01() }

class Day01 : Puzzle<Int, Int>(year = 2022, day = 1) {
    private val cals = rawInput
        .trimEnd()
        .splitToSequence("\n\n")
        .map { e -> e.split("\n").map { it.toInt() } }

    override fun solvePartOne(): Int {
        return cals.maxOf { items -> items.sum() }
    }

    override fun solvePartTwo(): Int {
        return cals.sortedByDescending { items -> items.sum() }.take(3).sumOf { it.sum() }
    }
}
