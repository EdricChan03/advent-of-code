package com.edricchan.aoc.year2021

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.solve
import com.edricchan.aoc.year2021.day02.SubmarineCommand

fun main() = solve { Day02() }

// "Value is always zero" error appears if destructuring is used, so suppress
// it.
// Note: This also appears to be a new inspection added in IntelliJ 2021.3.
@Suppress("KotlinConstantConditions")
class Day02 : Puzzle<Int, Int>(year = 2021, day = 2) {
    private val commands = input.map { SubmarineCommand(it) }
    override fun solvePartOne(): Int {
        var horizPos = 0
        var depth = 0

        commands.forEach { (type, number) ->
            when (type) {
                SubmarineCommand.CommandType.Forward -> {
                    // Horizontal position
                    horizPos += number
                }
                SubmarineCommand.CommandType.Down -> {
                    // Depth increase
                    depth += number
                }
                SubmarineCommand.CommandType.Up -> {
                    // Depth decrease
                    depth -= number
                }
            }
        }

        return horizPos * depth
    }

    override fun solvePartTwo(): Int {
        var horizPos = 0
        var depth = 0
        var aim = 0

        commands.forEach { (type, number) ->
            when (type) {
                SubmarineCommand.CommandType.Down -> {
                    // Increase aim
                    aim += number
                }
                SubmarineCommand.CommandType.Up -> {
                    // Decrease aim
                    aim -= number
                }
                SubmarineCommand.CommandType.Forward -> {
                    // 1. Increase horizontal position
                    // 2. Increase depth by (aim * units)
                    horizPos += number
                    depth += aim * number
                }
            }
        }

        return horizPos * depth
    }
}
