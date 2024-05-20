package com.edricchan.aoc.year2021

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.solve

fun main() = solve { Day06() }

class Day06 : Puzzle<Int, Long>(year = 2021, day = 6) {
    private val inputTimers =
        input.map { line -> line.split(",").map { it.toInt() } }.first()

    // For part 1 (unoptimised version)
    private fun simulate(days: Int): List<Int> {
        val currTimers = inputTimers.toMutableList()
        var currDay = 0

        do {
            val newTimers = mutableListOf<Int>()
            for (index in currTimers.indices) {
                if (currTimers[index] == 0) {
                    // Reset timer
                    currTimers[index] = 6
                    // Add new fish
                    newTimers += 8
                } else {
                    currTimers[index]--
                }
            }

            // Add the new fishes to the list
            currTimers += newTimers

            currDay++
        } while (currDay < days)

        return currTimers.toList()
    }

    // For part 2 (uses a map to store the count of all timers)
    fun simulate2(days: Int): Map<Int, Long> {
        var currTimers =
            inputTimers.groupingBy { it }.eachCount().toMutableMap().mapValues { it.value.toLong() }

        var currDay = 0

        do {
            val newTimers = mutableMapOf<Int, Long>()

            currTimers.toSortedMap().entries.forEach { (key, value) ->
                if (key == 0) {
                    // Reset timers
                    newTimers[6] = newTimers.getOrDefault(6, 0) + value
                    newTimers[0] = 0
                    // Add new fish
                    newTimers[8] = newTimers.getOrDefault(8, 0) + value
                } else {
                    newTimers[key - 1] = newTimers.getOrDefault(key - 1, 0) + value
//                    newTimers[key] = 0
                }
            }

            currTimers = newTimers

            currDay++
        } while (currDay < days)

        return currTimers
    }

    override fun solvePartOne(): Int {
        val currTimers = simulate(80)
        return currTimers.size
    }

    override fun solvePartTwo(): Long {
        // Part 2 expects 256 days
        val currTimers = simulate2(256)
        return currTimers.values.sum()
    }
}
