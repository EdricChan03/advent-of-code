package com.edricchan.aoc.year2024

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.solve
import com.edricchan.aoc.utils.splitWhitespace
import kotlin.math.abs

fun main() = solve(block = ::Day02)

private fun Int.isSafe() = abs(this) in 1..3

private fun DiffList.isMonotonic() = list.all { (it > 0) == (list[0] > 0) }

private fun DiffList.isSafe() = list.all { it.isSafe() }

@JvmInline
private value class DiffList(val list: List<Int>)

private fun List<Int>.bruteForceMonotonic(): Boolean {
    repeat(size) {
        if (filterIndexed { index, _ -> index != it }.toDifferences().run { isMonotonic() && isSafe() }) return true
    }

    return false
}

private fun String.toValues() = splitWhitespace()
    .map(String::toInt)

private fun List<Int>.toDifferences() = DiffList(zipWithNext { a, b -> a - b })
private fun String.toDifferences() = toValues()
    .toDifferences()

class Day02 : Puzzle<Int, Int>(
    year = 2024, day = 2
) {
    override fun solvePartOne(): Int = useNotBlankInput { input ->
        input.map { it.toDifferences() }
            .count { values -> values.isMonotonic() && values.isSafe() }
    }

    override fun solvePartTwo(): Int = useNotBlankInput { input ->
        input.map { it.toValues() }
            .count { values ->

                val initialDiff = values.toDifferences()
                // Conditions already met
                if (initialDiff.isMonotonic() && initialDiff.isSafe()) return@count true

                values.bruteForceMonotonic()
            }
    }
}
