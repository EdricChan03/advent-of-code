package com.edricchan.aoc.year2023

import com.edricchan.aoc.Puzzle
import com.edricchan.aoc.solve
import com.edricchan.aoc.year2023.day01.allDigitsMap

fun main() = solve(block = ::Day01)

class Day01 : Puzzle<Int, Int>(year = 2023, day = 1) {
    override fun solvePartOne() = useInput { lines ->
        lines
            // Remove any empty lines (e.g. end-of-line)
            .filter { it.isNotBlank() }
            .map { line ->
                // Remove all non-digits
                val result = line.filter { it.isDigit() }
                // Skip lines that have no numbers (for part 2 test input)
                if (result.isEmpty()) return@map 0
                // Then concat the first + last digits
                "${result.first()}${result.last()}".toInt()
            }.sum()
    }

    override fun solvePartTwo() = useInput { lines ->
        lines
            .filter { it.isNotBlank() }
            .map { line ->
                // Convert each letter-form to its digit form
                val result = allDigitsMap.mapValues { (word, _) ->
                    word.toRegex().findAll(line).map { it.range }.toList()
                }
                    // Remove empty matches
                    .filterValues { it.isNotEmpty() }

                val (firstWord, _) = result.toList().minBy { (_, ranges) ->
                    ranges.minBy { it.first }.first
                }

                val (lastWord, _) = result.toList().maxBy { (_, ranges) ->
                    ranges.maxBy { it.last }.last
                }

                val first = allDigitsMap[firstWord]!!
                val last = allDigitsMap[lastWord]!!
                first * 10 + last
            }.sum()
    }

}
