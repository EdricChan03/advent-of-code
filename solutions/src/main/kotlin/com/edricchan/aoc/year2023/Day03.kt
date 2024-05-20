package com.edricchan.aoc.year2023

import com.edricchan.aoc.model.geom.line.adjacentPoints
import com.edricchan.aoc.model.geom.point.Point
import com.edricchan.aoc.model.geom.point.adjacentPoints
import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.input.PuzzleInput
import com.edricchan.aoc.puzzle.solve
import com.edricchan.aoc.utils.hasCommonElementsWith
import com.edricchan.aoc.utils.productOf
import com.edricchan.aoc.year2023.day03.*

fun main() = solve(block = ::Day03)

private val testInput = PuzzleInput.ResourceFile("aoc/year2023/day3/test-input.txt")

class Day03 : Puzzle<Int, Int>(year = 2023, day = 3, inputData = testInput) {

    private fun List<String>.getPartNumbers() = flatMapIndexed { index, line ->
        line.findAllPartNumbers()
            .map {
                PartNumber(
                    number = it.value.toInt(),
                    startPosX = it.range.first,
                    startPosY = index,
                    endPosX = it.range.last,
                    endPosY = index
                )
            }
    }

    override fun solvePartOne(): Int {
        val symbols = input.flatMapIndexed { index, line ->
            line.findAllSymbols()
                .map { Symbol(char = it.value, posX = it.range.first, posY = index) }
                .toList()
        }

        val numbers = input.getPartNumbers()

        val filtered = numbers.filter { number ->
            number.bounds.adjacentPoints() hasCommonElementsWith symbols.map(Symbol::position)
        }
        return filtered.sumOf(PartNumber::number)
    }

    override fun solvePartTwo(): Int {
        val gears = input.flatMapIndexed { index, line ->
            line.findAllGears()
                .map { Point(x = it.range.first, y = index) }
                .toList()
        }

        val partNumbers = input.getPartNumbers()

        val filtered = gears.associateWith { gear ->
            gear.adjacentPoints().flatMap { it.getIntersectingPartNumbers(partNumbers) }.distinct()
        }.filterValues { it.size == 2 }
        return filtered.values.sumOf { gearValue -> gearValue.productOf(PartNumber::number) }
    }
}
