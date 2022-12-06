package com.edricchan.aoc.year2022

import com.edricchan.aoc.Puzzle
import com.edricchan.aoc.solve
import com.edricchan.aoc.utils.firstZipWithNext
import com.edricchan.aoc.utils.map
import com.edricchan.aoc.year2022.day05.toCrateStacks
import com.edricchan.aoc.year2022.day05.toMoveStep

fun main() = solve { Day05() }

class Day05 : Puzzle<String, String>(year = 2022, day = 5) {
    private val mappedInput = rawInput
        .trimEnd()
        .split("\n\n")
        .firstZipWithNext()
        .map { (first, second) ->
            first.toCrateStacks() to second.lines().mapNotNull(String::toMoveStep)
        }

    override fun solvePartOne(): String {
        val (cratesGrid, steps) = mappedInput
        // println("Crate grid: $cratesGrid")
        val mutableCrates = cratesGrid.map { it.toMutableList() }.toMutableList()
        steps.forEach {
            // println("Current move step: $it, current crates: $mutableCrates")
            val fromStack = mutableCrates[it.fromStack - 1]
            val crates = fromStack.asReversed().subList(fromStack.size - it.amount, fromStack.size)
            // println("Crates to move: $crates")
            val currToCrates = mutableCrates[it.toStack - 1]
            mutableCrates[it.toStack - 1] = (crates + currToCrates).toMutableList()
            crates.clear()
            // println("Final crates: $mutableCrates")
        }

        return mutableCrates.map { it.first().crate }.joinToString("")
    }

    override fun solvePartTwo(): String {
        val (cratesGrid, steps) = mappedInput
        // println("Crate grid: $cratesGrid")
        val mutableCrates = cratesGrid.map { it.toMutableList() }.toMutableList()
        steps.forEach {
            // println("Current move step: $it, current crates: $mutableCrates")
            val fromStack = mutableCrates[it.fromStack - 1]
            val crates = fromStack.asReversed().subList(fromStack.size - it.amount, fromStack.size)
            // println("Crates to move: $crates")
            val currToCrates = mutableCrates[it.toStack - 1]
            mutableCrates[it.toStack - 1] = (crates.reversed() + currToCrates).toMutableList()
            crates.clear()
            // println("Final crates: $mutableCrates")
        }

        return mutableCrates.map { it.first().crate }.joinToString("")
    }
}
