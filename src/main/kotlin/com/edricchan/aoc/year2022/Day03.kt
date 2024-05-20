package com.edricchan.aoc.year2022

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.solve
import com.edricchan.aoc.utils.splitEvenHalf
import com.edricchan.aoc.year2022.day03.priorityList

fun main() = solve { Day03() }

class Day03 : Puzzle<Int, Int>(year = 2022, day = 3) {
    override fun solvePartOne() = useInput { lines ->
        lines.map { it.splitEvenHalf { str -> str.toList() } }
            .flatMap { (left, right) -> left intersect right.toSet() }
            .map { priorityList.first { (c, _) -> it == c }.second }
            .sum()
    }

    override fun solvePartTwo() = useInput { lines ->
        lines
            .map { it.toList() }
            .chunked(3)
            .map {
                it.reduce { acc, chars ->
                    (acc intersect chars.toSet()).toList()
                }.first()
            }
            .map { priorityList.first { (c, _) -> it == c }.second }
            .sum()
    }
}
