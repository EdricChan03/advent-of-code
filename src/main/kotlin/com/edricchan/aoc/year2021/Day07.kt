package com.edricchan.aoc.year2021

import com.edricchan.aoc.Puzzle
import com.edricchan.aoc.solve
import kotlin.math.absoluteValue

fun main() = solve { Day07() }

class Day07(private val maxIterations: Int = 10000) : Puzzle<Int, Long>(year = 2021, day = 7) {
    private val positions =
        input.map { line -> line.split(",").map { it.toInt() } }.first()

    /** Returns a map of positions as the keys and (count of positions, move count) as the values. */
    private fun getMoveCount(alignPos: Int): Map<Int, Pair<Int, Int>> {
        val posValues = positions.groupingBy { it }.eachCount()
        return posValues.mapValues { it.value to (it.key - alignPos).absoluteValue }
    }

    override fun solvePartOne(): Int {
        var cheapestTotal = Int.MAX_VALUE

        for (i in 0..maxIterations) {
            val total = getMoveCount(i).values.sumOf { it.first * it.second }
            if (cheapestTotal > total) cheapestTotal = total
        }

        return cheapestTotal
    }

    override fun solvePartTwo(): Long {
        var cheapestTotal = Long.MAX_VALUE

        for (i in 0..maxIterations) {
            val total = getMoveCount(i).values.sumOf {
                var currTotal = 0L
                for (j in 1..it.second) {
                    currTotal += j
                }
                return@sumOf (it.first * currTotal)
            }

            if (cheapestTotal > total) cheapestTotal = total
        }

        return cheapestTotal
    }

}
