package com.edricchan.aoc.year2025

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.solve
import com.edricchan.aoc.utils.splitEvenHalf
import com.edricchan.aoc.utils.splitPair
import kotlin.math.abs
import kotlin.math.log10

fun main() = solve(block = ::Day02)

private fun Long.countDigits() = when (this) {
    0L -> 1
    else -> log10(abs(toDouble())).toInt() + 1
}

class Day02 : Puzzle<Long, Long>(
    year = 2025,
    day = 2
) {
    private val ranges = input.single()
        .split(",")
        .map { range -> range.splitPair("-") { it.toLong() } }

    override fun solvePartOne(): Long {
        return ranges.sumOf { (start, end) ->
            (start..end).filter { it.countDigits() % 2 == 0 }.filter {
                val (left, right) = it.toString().splitEvenHalf()
                left == right
            }.sum()
        }
    }

    override fun solvePartTwo(): Long {
        return ranges.sumOf { (start, end) ->
            (start..end).filter { num ->
                val digits = num.countDigits()
                val numStr = num.toString()
                (1..<digits)
                    .filter { digits % it == 0 }
                    .any { repeatAttempts ->
                        val attempt = (numStr.take(repeatAttempts))
                        val times = digits / repeatAttempts
                        attempt.repeat(times) == numStr
                    }
            }.sum()
        }
    }
}
