package com.edricchan.aoc.year2021

import com.edricchan.aoc.Puzzle
import com.edricchan.aoc.solve

fun main() = solve { Day01() }

class Day01 : Puzzle<Int, Int>(year = 2021, day = 1) {
    private val records = input.map { it.toInt() }
    override fun solvePartOne(): Int {
        var prevHighest = records.first()
        var increaseTimes = 0

        records.slice(1..records.lastIndex).forEach {
            // println("Received number $it, previous highest number = $highestNum," +
            //    " current no of times increased = $increaseTimes")
            if (it > prevHighest) {
                // Current record has increased
                increaseTimes++
            }
            // Reset highest number
            prevHighest = it
        }

        return increaseTimes
    }

    override fun solvePartTwo(): Int {
        // Can be done with Kotlin's Collections API
        // windowed(3): [1,2,3,4,5,3] -> [[1,2,3], [2,3,4], [3,4,5], [4,5,3]]
        // windowed(3) { it.sum() }: [[1,2,3], [2,3,4], [3,4,5], [4,5,3]] -> [6,9,12,12]
        return records.windowed(3) { it.sum() }
            // zipWithNext: [6,9,12,12] -> [6 to 9, 9 to 12, 12 to 12]
            .zipWithNext()
            // count: [6 to 9, 9 to 12, 12 to 15] -> 9 > 6? yes, 12 > 9? yes, 12 > 12? no
            // two occurrences
            .count { (prev, next) -> next > prev }
    }
}
