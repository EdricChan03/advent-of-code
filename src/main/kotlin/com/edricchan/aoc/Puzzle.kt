package com.edricchan.aoc

/**
 * The puzzle question for a specific day.
 * @property year The specific year
 * @property day The specific day
 * @property PartOneOutput The output type to be used for part 1
 * @property PartTwoOutput The output type to be used for part 2
 */
abstract class Puzzle<PartOneOutput, PartTwoOutput>(
    val year: Int = Int.MIN_VALUE, val day: Int = Int.MIN_VALUE
) {
    /** The input for the specific puzzle. */
    val input = getInput(year, day)

    /** Retrieves the solution for part 1 of type [PartOneOutput]. */
    abstract fun solvePartOne(): PartOneOutput

    /** Retrieves the solution for part 2 of type [PartTwoOutput]. */
    abstract fun solvePartTwo(): PartTwoOutput
}
