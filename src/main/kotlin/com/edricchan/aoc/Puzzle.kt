package com.edricchan.aoc

import java.time.LocalDate
import java.time.Year

/**
 * The puzzle question for a specific day.
 * @property year The specific year
 * @property day The specific day
 * @property customInput The custom input, if any. If specified, this value
 * takes precedence over the [input] property.
 * @property inputFileName The input's file name. By default, this value is set to
 * `"input.txt"`.
 * @property PartOneOutput The output type to be used for part 1
 * @property PartTwoOutput The output type to be used for part 2
 */
abstract class Puzzle<PartOneOutput, PartTwoOutput>(
    val year: Int = Year.now().value, val day: Int = LocalDate.now().dayOfMonth,
    private val customInput: String? = null,
    private val inputFileName: String = "input.txt"
) {
    /** The input [File] for the specific puzzle. */
    val inputFile by lazy { getInputFile(year, day, inputFileName) }

    /** The input for the specific puzzle as a [List] of strings. */
    val input by lazy { customInput?.lines() ?: getInput(year, day) }

    /**
     * Allows for the input to be retrieved as a [Sequence] via [block],
     * closing the file once done processing.
     * @param block The callback to execute, where the contents of the file are
     * passed as a [Sequence].
     * @return The result as returned by executing [block].
     */
    fun <T> useInput(block: (Sequence<String>) -> T) = useInput(year, day, block = block)

    /** The input for the specific puzzle as a [String]. */
    val rawInput by lazy { customInput ?: getRawInput(year, day) }

    /** Retrieves the solution for part 1 of type [PartOneOutput]. */
    abstract fun solvePartOne(): PartOneOutput

    /** Retrieves the solution for part 2 of type [PartTwoOutput]. */
    abstract fun solvePartTwo(): PartTwoOutput
}

/** Metadata for a [Puzzle]. This should be implemented by classes that extend from [Puzzle]. */
data class PuzzleMeta(
    val year: Year,
    val day: Int
) {
    constructor(year: Int, day: Int) : this(Year.of(year), day)
}
