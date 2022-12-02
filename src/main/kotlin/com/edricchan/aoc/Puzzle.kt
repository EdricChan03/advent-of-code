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
    /** The input [File] for the specific puzzle. */
    val inputFile by lazy { getInputFile(year, day) }

    /** The input for the specific puzzle as a [List] of strings. */
    val input by lazy { getInput(year, day) }

    /**
     * Allows for the input to be retrieved as a [Sequence] via [block],
     * closing the file once done processing.
     * @param block The callback to execute, where the contents of the file are
     * passed as a [Sequence].
     * @return The result as returned by executing [block].
     */
    fun <T> useInput(block: (Sequence<String>) -> T) = useInput(year, day, block = block)

    /** The input for the specific puzzle as a [String]. */
    val rawInput by lazy { getRawInput(year, day) }

    /** Retrieves the solution for part 1 of type [PartOneOutput]. */
    abstract fun solvePartOne(): PartOneOutput

    /** Retrieves the solution for part 2 of type [PartTwoOutput]. */
    abstract fun solvePartTwo(): PartTwoOutput
}

/** Metadata for a [Puzzle]. This should be implemented by classes that extend from [Puzzle]. */
data class PuzzleMeta(
    val year: Int,
    val day: Int
)
