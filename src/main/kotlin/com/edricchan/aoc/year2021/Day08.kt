package com.edricchan.aoc.year2021

import com.edricchan.aoc.Puzzle
import com.edricchan.aoc.solve

fun main() = solve { Day08() }

private typealias Digit = Set<Char>

// Code from
// https://github.com/edgars-supe/advent-of-code/blob/master/src/main/kotlin/lv/esupe/aoc/year2021/Day8.kt,
// but with additional annotations
class Day08 : Puzzle<Int, Int>(year = 2021, day = 8) {
    private val items = input.map { line ->
        val (patterns, output) = line.split(" | ")
        Entry(
            patterns.split(" ").map { it.toSet() },
            output.split(" ").map { it.toSet() }
        )
    }
    override fun solvePartOne(): Int {
        // 1 -> 2 segments, 4 -> 4 segments, 7 -> 3 segments, 8 -> 7 segments
        val validSegments = listOf(2, 4, 3, 7)
        return items.flatMap { it.output }.count { it.size in validSegments }
    }

    override fun solvePartTwo(): Int {
        /*
        - acedgfb: 8
        - cdfbe: 5
        - gcdfa: 2
        - fbcad: 3
        - dab: 7
        - cefabd: 9
        - cdfgeb: 6
        - eafb: 4
        - cagedb: 0
        - ab: 1
         */
        return items
            .map { entry ->
                fun getPattern(length: Int, predicate: (Digit) -> Boolean = { true }): Digit {
                    return entry.patterns.first { it.size == length && predicate(it) }
                }

                // ab (common in all other patterns)
                val one = getPattern(length = 2)
                // abfe
                val four = getPattern(length = 4)
                // abd
                val seven = getPattern(length = 3)
                // abcdefg
                val eight = getPattern(length = 7)

                // abcdef -> intersects with abd (7) + abfe (4)
                val nine = getPattern(length = 6) { it intersect four == four }
                // abcdeg -> intersects with abd (7), but not abfe (4)
                val zero = getPattern(length = 6) { it != nine && it intersect seven == seven }
                // bcdefg -> no intersects
                val six = getPattern(length = 6) { it != zero && it != nine }

                // abcdf -> intersects with abd (7)
                val three = getPattern(length = 5) { it intersect seven == seven }
                // bcdef -> intersects with abcdef (9)
                val five = getPattern(length = 5) { it != three && nine intersect it == it }
                // acdfg -> no intersects
                val two = getPattern(length = 5) { it != three && it != five }

                val digits = listOf(zero, one, two, three, four, five, six, seven, eight, nine)
                // e.g. 12345 -> 0 * 10 + ... -> (0 * 10 + ...) * 10 + ... -> ((0 * 10 + ...) * 10 + ...) + ...
                entry.output.fold(0) { acc, pattern -> acc * 10 + digits.indexOf(pattern) }
            }
            .sum()
    }

    private data class Entry(
        val patterns: List<Digit>,
        val output: List<Digit>
    )
}
