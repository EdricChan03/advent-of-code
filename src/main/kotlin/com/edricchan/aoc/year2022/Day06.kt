package com.edricchan.aoc.year2022

import com.edricchan.aoc.Puzzle
import com.edricchan.aoc.solve
import com.edricchan.aoc.utils.isUnique

fun main() = solve { Day06() }

class Day06(dayInput: String? = null) : Puzzle<Int, Int>(
    year = 2022, day = 6, customInput = dayInput
) {
    private fun getMarkerIndex(bufferSize: Int): Int {
        val buffer = input
            .first() // Get the first line
            .windowedSequence(bufferSize) { it.toList() }
            .withIndex()
            .first { it.value.isUnique }
        return buffer.index + bufferSize
    }

    override fun solvePartOne() = getMarkerIndex(4)

    override fun solvePartTwo() = getMarkerIndex(14)
}
