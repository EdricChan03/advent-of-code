package com.edricchan.aoc.year2024

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.solve
import com.edricchan.aoc.utils.middle
import com.edricchan.aoc.year2024.day05.Manual

fun main() = solve(block = ::Day05)

class Day05 : Puzzle<Int, Int>(
    year = 2024, day = 5
) {
    override fun solvePartOne(): Int {
        return Manual.fromString(rawInput.trim()).validUpdates.sumOf { it.middle() }
    }

    override fun solvePartTwo(): Int {
        val manual = Manual.fromString(rawInput.trim())

        val mergedRules = manual.rules.groupBy(
            keySelector = { it.first },
            valueTransform = { it.second }
        ).mapValues { it.value.sorted() }

        val fixedUpdates = manual.invalidUpdates.map {
            it.sortedWith { o1, o2 ->
                if (mergedRules.getOrDefault(o1, emptyList()).contains(o2)) 1
                else -1
            }
        }
        return fixedUpdates.sumOf { it.middle() }
    }
}
