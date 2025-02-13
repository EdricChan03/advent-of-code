package com.edricchan.aoc.year2024

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.solve
import com.edricchan.aoc.utils.splitPair
import com.edricchan.aoc.year2024.day07.OperandPart1
import com.edricchan.aoc.year2024.day07.OperandPart2

fun main() = solve(block = ::Day07)

fun <T> cartesianProduct(elements: List<T>, length: Int): List<List<T>> {
    if (length == 1) return elements.map { listOf(it) }
    return elements.flatMap { element ->
        cartesianProduct(elements, length - 1).map { listOf(element) + it }
    }
}

private fun Sequence<String>.toInputPairs() = map { line ->
    line.splitPair(
        ":",
        transformLeft = { it.toLong() },
        transformRight = { nums -> nums.trim().split(" ").map { it.toLong() } })
}

class Day07 : Puzzle<Long, Long>(
    year = 2024, day = 7
) {
    override fun solvePartOne(): Long = useInput { lines ->
        lines.toInputPairs().filter { (solution, nums) ->
            val permutations = cartesianProduct(OperandPart1.entries, nums.size - 1)
            permutations.any { ops ->
                val value = ops.foldIndexed(nums.first()) { index, acc, op ->
                    op.calculate(acc, nums[index + 1])
                }

                value == solution
            }
        }.sumOf { it.first }
    }

    // Takes 7 seconds...
    // Probably can be optimised
    override fun solvePartTwo(): Long = useInput { lines ->
        lines.toInputPairs().filter { (solution, nums) ->
            val permutations = cartesianProduct(OperandPart2.entries, nums.size - 1)
            permutations.any { ops ->
                val value = ops.foldIndexed(nums.first()) { index, acc, op ->
                    op.calculate(acc, nums[index + 1])
                }

                value == solution
            }
        }.sumOf { it.first }
    }
}
