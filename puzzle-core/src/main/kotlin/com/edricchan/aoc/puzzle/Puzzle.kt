package com.edricchan.aoc.puzzle

import com.edricchan.aoc.puzzle.input.PuzzleInput
import java.time.LocalDate
import java.time.Year

/**
 * The puzzle question for a specific day.
 * @property year The specific year
 * @property day The specific day
 * @property inputData The [PuzzleInput] type to use.
 * If not specified, this value is set to [PuzzleInput.Default] with the [year] and
 * [day] arguments specified respectively.
 *
 * To set the [PuzzleInput.Default.inputFileName] argument as well, use the overload
 * of [Puzzle] which accepts an `inputFileName`.
 * @param PartOneOutput The output type to be used for part 1
 * @param PartTwoOutput The output type to be used for part 2
 */
public abstract class Puzzle<PartOneOutput, PartTwoOutput>(
    public val year: Int = Year.now().value, public val day: Int = LocalDate.now().dayOfMonth,
    public val inputData: PuzzleInput = PuzzleInput.Default(Year.of(year), day),
) {
    public constructor(
        year: Int,
        day: Int,
        // If this constructor is used, we can assume that we're explicitly passing
        // an inputFileName, so there's no default value (to prevent overload
        // resolution ambiguity).
        inputFileName: String
    ) : this(
        year = year,
        day = day,
        inputData = PuzzleInput.Default(year, day, inputFileName)
    )

    /** This puzzle's metadata. */
    public val meta: PuzzleMeta = PuzzleMeta(Year.of(year), day)

    /** The input for the specific puzzle as a [List] of strings. */
    public val input: List<String> by lazy { inputData.lines() }

    /**
     * Allows for the input to be retrieved as a [Sequence] via [block],
     * closing the file once done processing.
     * @param block The callback to execute, where the contents of the file are
     * passed as a [Sequence].
     * @return The result as returned by executing [block].
     */
    public fun <T> useInput(block: (Sequence<String>) -> T): T = inputData.useLines(block)

    /**
     * Allows for the input to be retrieved as a [Sequence] via [block],
     * closing the file once done processing.
     *
     * Variant of [useInput] which attempts to remove all empty lines.
     * (For example, the end-of-file trailing new-line which might be added by
     * default with tools like [EditorConfig](https://editorconfig.org).)
     *
     * This method is equivalent to the following code:
     * ```kotlin
     * useInput { lines ->
     *     block(lines.filter { it.isNotBlank() })
     * }
     * ```
     * @param block The callback to execute, where the contents of the file are
     * passed as a [Sequence].
     * @return The result as returned by executing [block].
     * @see useInput
     * @see Sequence.filter
     * @see String.isNotBlank
     */
    public fun <T> useNotBlankInput(block: (Sequence<String>) -> T): T = useInput { lines ->
        block(lines.filter { it.isNotBlank() })
    }

    /**
     * The input for the specific puzzle as a [String].
     *
     * To get the input split by line-endings, use [input] or [useInput] instead.
     */
    public val rawInput: String by lazy { inputData.raw() }

    /** Retrieves the solution for part 1 of type [PartOneOutput]. */
    public abstract fun solvePartOne(): PartOneOutput

    /** Retrieves the solution for part 2 of type [PartTwoOutput]. */
    public abstract fun solvePartTwo(): PartTwoOutput

    public companion object {
        /** The default input file name if not specified. */
        public const val defaultInputFileName: String = "input.txt"
    }
}

/** Metadata for a [Puzzle]. This should be implemented by classes that extend from [Puzzle]. */
public data class PuzzleMeta(
    val year: Year,
    val day: Int
) {
    public constructor(year: Int, day: Int) : this(Year.of(year), day)
}

/**
 * Retrieves the default [PuzzleInput] from the receiver [PuzzleMeta].
 * @param inputFileName The input's file name to be used.
 * @see PuzzleInput.Default
 */
public fun PuzzleMeta.getDefaultInput(
    inputFileName: String = "input.txt"
): PuzzleInput.Default = PuzzleInput.Default(meta = this, inputFileName = inputFileName)

/**
 * Retrieves the default [PuzzleInput] from the receiver [PuzzleMeta] using the
 * default `input.txt` file name.
 * @see PuzzleInput.Default
 * @see PuzzleMeta.getDefaultInput
 */
public val PuzzleMeta.defaultInput: PuzzleInput.Default get() = getDefaultInput()

/**
 * Retrieves the default [PuzzleInput] from the receiver [Puzzle].
 * @param inputFileName The input's file name to be used.
 * @see PuzzleInput.Default
 */
public fun Puzzle<*, *>.getDefaultInput(
    inputFileName: String = "input.txt"
): PuzzleInput.Default = PuzzleMeta(
    year = year,
    day = day
).getDefaultInput(inputFileName)

/**
 * Retrieves the default [PuzzleInput] from the receiver [Puzzle] using the default
 * `input.txt` file name.
 * @see PuzzleInput.Default
 * @see Puzzle.getDefaultInput
 */
public val Puzzle<*, *>.defaultInput: PuzzleInput.Default get() = getDefaultInput()
