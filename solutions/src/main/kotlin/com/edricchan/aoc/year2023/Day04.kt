package com.edricchan.aoc.year2023

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.input.PuzzleInput
import com.edricchan.aoc.puzzle.solve
import com.edricchan.aoc.utils.splitWhitespace
import kotlin.math.pow

fun main() = solve(block = ::Day04)

//private val input = PuzzleInput.ResourceFile("aoc/year2023/day4/test-input.txt")
private val input = PuzzleInput.Default(2023, 4)

class Day04 : Puzzle<Int, Int>(year = 2023, day = 4, inputData = input) {
    private fun Sequence<String>.mapCards() = map { line ->
        val (winning, curr) = line.split(" | ")
        winning
            .substring(winning.indexOf(':') + 2)
            .toNumbers() to curr.toNumbers()
    }

    private fun String.toNumbers() = splitWhitespace().mapNotNull(String::toIntOrNull)

    override fun solvePartOne() = useInput { lines ->
        lines.mapCards().sumOf { (winning, curr) ->
            val currWinning = winning intersect curr.toSet()
            if (currWinning.isEmpty()) return@sumOf 0.0
            2.0.pow(currWinning.size - 1)
        }.toInt()
    }

    override fun solvePartTwo(): Int = useInput { lines ->
        val resultCardIndices = mutableMapOf</* card */ Int, /* count */ Int>()
        // Initialise the map to `1` for every index
        resultCardIndices.putAll(List(input.size) { index -> index to 1 })

        lines.mapCards().foldIndexed(resultCardIndices) { index, indicesMap, (winning, curr) ->
            val currWinning = (winning intersect curr.toSet()).size
            for (i in (index + 1)..(index + currWinning)) {
                indicesMap[i] = indicesMap.getValue(i) + 1 * indicesMap.getValue(index)
            }
            indicesMap
        }.values.sum()
    }
}
