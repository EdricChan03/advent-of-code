package com.edricchan.aoc.year2024

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.solve
import com.edricchan.aoc.utils.mapValues
import com.edricchan.aoc.utils.splitWhitespace
import kotlin.math.abs

fun main() = solve(block = ::Day01)

private fun List<String>.toPairedLists(): Pair<List<Int>, List<Int>> = flatMap { it.splitWhitespace() }
    .map { it.toInt() }
    .withIndex()
    .partition { it.index % 2 == 0 }
    .mapValues { it.map(IndexedValue<Int>::value).sorted() }

class Day01 : Puzzle<Int, Int>(year = 2024, day = 1) {
    override fun solvePartOne(): Int {
        val (left, right) = input.toPairedLists()

        return left.zip(right) { leftInt, rightInt ->
            abs(leftInt - rightInt)
        }.sum()
    }

    override fun solvePartTwo(): Int {
        val (left, right) = input.toPairedLists()

        return left.sumOf { value  -> right.count { it == value } * value }
    }
}
