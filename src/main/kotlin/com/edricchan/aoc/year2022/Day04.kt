package com.edricchan.aoc.year2022

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.solve
import com.edricchan.aoc.utils.*

fun main() = solve { Day04() }

private fun String.toRange() = split("-")
    .firstZipWithNext()
    .map { (low, up) -> low.toInt()..up.toInt() }

private fun Sequence<String>.asInput() = map {
    it.split(",").firstZipWithNext()
        .mapValues(String::toRange)
}

class Day04 : Puzzle<Int, Int>(year = 2022, day = 4) {
    override fun solvePartOne() = useInput { lines ->
        lines.asInput()
            .map { it.hasFullOverlap }
            .countTrue()
    }

    override fun solvePartTwo() = useInput { lines ->
        lines.asInput()
            .map { it.hasPartialOverlap }
            .countTrue()
    }
}
