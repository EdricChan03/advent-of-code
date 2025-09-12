package com.edricchan.aoc.puzzle

import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.io.path.useLines
import kotlin.time.measureTimedValue

/** A resource loader used to retrieve resources. */
public fun interface ResourceLoader {
    /** Retrieves the specified resource given the [file name][fileName]. */
    public fun getResource(fileName: String): File?

    /** Retrieves the specified resource given the [file name][fileName] as a [Path]. */
    public fun getResourceAsPath(fileName: String): Path? = getResource(fileName)?.toPath()

    /**
     * Retrieves the specified resource given the [file name][fileName].
     *
     * If `null` is returned, a runtime [IllegalArgumentException] is thrown.
     * @see requireNotNull
     * @see getResource
     */
    public fun requireResource(fileName: String): File = requireNotNull(getResource(fileName)) {
        "Resource with file name $fileName does not exist"
    }

    /**
     * Retrieves the specified resource given the [file name][fileName] as a [Path].
     *
     * If `null` is returned, a runtime [IllegalArgumentException] is thrown.
     * @see requireNotNull
     * @see getResource
     */
    public fun requireResourceAsPath(fileName: String): Path = requireNotNull(getResourceAsPath(fileName)) {
        "Resource with file name $fileName does not exist"
    }

    /** The default resource loader. */
    public object Default : ResourceLoader {
        override fun getResource(fileName: String): File? {
            return this::class.java.classLoader.getResource(fileName)?.toURI()?.let { File(it) }
        }
    }
}

/**
 * Retrieves the file path for the given [day] and [year], optionally allowing
 * for the [fileName] to be specified.
 *
 * This is equivalent to the string `"aoc/year$year/day$day/$fileName"`.
 *
 * To get the input as a [File], use [getInputFile]. To get the input as a [Path],
 * use [getInputPath].
 * @param year The puzzle's year.
 * @param day The puzzle's year.
 * @param fileName The text file's name.
 */
public fun getInputFilePath(
    year: Int, day: Int, fileName: String = "input.txt"
): String = "aoc/year$year/day$day/$fileName"

/**
 * Retrieves the file path for the given [PuzzleMeta], optionally allowing
 * for the [fileName] to be specified.
 *
 * This is equivalent to the string `"aoc/year${meta.year}/day${meta.day}/$fileName"`.
 *
 * To get the input as a [File], use [getInputFile].
 * To get the input as a [Path], use [getInputPath].
 * @receiver The puzzle's metadata, consisting of [PuzzleMeta.year] and [PuzzleMeta.day].
 * @param fileName The text file's name.
 */
public fun PuzzleMeta.getInputFilePath(
    fileName: String = "input.txt"
): String = getInputFilePath(year = year.value, day = day, fileName = fileName)

/**
 * Retrieves the input text file for a given [day] and [year] as a [File].
 * @param year The year of the puzzle.
 * @param day The day of the puzzle.
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @return The input text file as a [File] reference.
 * @see getInputPath
 * @see getInputFilePath
 */
public fun getInputFile(
    year: Int, day: Int, fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
): File = resourceLoader.getResource(getInputFilePath(year, day, fileName))
    ?: throw FileNotFoundException("Input file for year $year, day $day does not exist")

/**
 * Retrieves the input text file for the receiver [PuzzleMeta] as a [File].
 * @receiver The puzzle metadata, consisting of [PuzzleMeta.year] and [PuzzleMeta.day].
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @return The input text file as a [File] reference.
 * @see getInputPath
 * @see getInputFilePath
 */
public fun PuzzleMeta.getInputFile(
    fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
): File = getInputFile(
    year = year.value, day = day, fileName = fileName, resourceLoader = resourceLoader
)

/**
 * Retrieves the input text file for a given [day] and [year] as a [Path].
 * @param year The year of the puzzle.
 * @param day The day of the puzzle.
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @return The input text file as a [Path] reference.
 * @see getInputFile
 * @see getInputFilePath
 */
public fun getInputPath(
    year: Int, day: Int, fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
): Path = resourceLoader.getResourceAsPath(getInputFilePath(year, day, fileName))
    ?: throw FileNotFoundException("Input file for year $year, day $day does not exist")

/**
 * Retrieves the input text file for the receiver [PuzzleMeta] as a [Path].
 * @receiver The puzzle's metadata, consisting of [PuzzleMeta.year] and [PuzzleMeta.day].
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @return The input text file as a [Path] reference.
 * @see getInputFile
 * @see getInputFilePath
 */
public fun PuzzleMeta.getInputPath(
    fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
): Path = getInputPath(
    year = year.value, day = day, fileName = fileName, resourceLoader = resourceLoader
)

/**
 * Retrieves the input text file's contents for a given [day] and [year].
 * @param year The year of the puzzle.
 * @param day The day of the puzzle.
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @return The contents of the text file as a list split by new lines.
 */
public fun getInput(
    year: Int, day: Int, fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
): List<String> = getInputPath(year, day, fileName, resourceLoader).readLines()

/**
 * Retrieves the input text file's contents for the receiver [PuzzleMeta].
 * @receiver The puzzle metadata, consisting of [PuzzleMeta.year] and [PuzzleMeta.day].
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @return The contents of the text file as a list split by new lines.
 */
public fun PuzzleMeta.getInput(
    fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
): List<String> = getInput(
    year = year.value, day = day, fileName = fileName, resourceLoader = resourceLoader
)

/**
 * Retrieves the input text file's contents for a given [day] and [year] as a [Sequence].
 * @param year The year of the puzzle.
 * @param day The day of the puzzle.
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @param block A callback where the contents of the file are passed as a [Sequence].
 * @return The result of the [block].
 */
public fun <T> useInput(
    year: Int, day: Int, fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default,
    block: (Sequence<String>) -> T
): T = getInputPath(year, day, fileName, resourceLoader).useLines(block = block)

/**
 * Retrieves the input text file's contents for the receiver [PuzzleMeta] as a [Sequence].
 * @receiver The puzzle metadata, consisting of [PuzzleMeta.year] and [PuzzleMeta.day].
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @param block A callback where the contents of the file are passed as a [Sequence].
 * @return The result of the [block].
 */
public fun <T> PuzzleMeta.useInput(
    fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default,
    block: (Sequence<String>) -> T
): T = useInput(
    year = year.value, day = day, fileName = fileName, resourceLoader = resourceLoader,
    block = block
)

/**
 * Retrieves the input text file's contents for a given [day] and [year].
 * @param year The year of the puzzle.
 * @param day The day of the puzzle.
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @return The contents of the text file as a [String].
 */
public fun getRawInput(
    year: Int, day: Int, fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
): String = getInputPath(year, day, fileName, resourceLoader).readText()

/**
 * Retrieves the input text file's contents for a given [this@getRawInput].
 * @receiver The puzzle metadata, consisting of [PuzzleMeta.year] and [PuzzleMeta.day].
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @return The contents of the text file as a [String].
 */
public fun PuzzleMeta.getRawInput(
    fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
): String = getRawInput(
    year = year.value, day = day, fileName = fileName, resourceLoader = resourceLoader
)

/**
 * Solves the specified puzzle.
 * @param benchmark Whether to perform benchmarks on the solution.
 * @param block The higher order function to be used to retrieve the [Puzzle].
 * @see printResult
 * @see printBenchmarkedResult
 */
public fun solve(benchmark: Boolean = true, block: () -> Puzzle<*, *>) {
    if (benchmark) printBenchmarkedResult(block)
    else printResult(block = block)
}

public fun printResult(block: () -> Puzzle<*, *>) {
    val puzzle = block()
    println("${puzzle.day} December ${puzzle.year} (input - ${puzzle.inputData.displayName}):")
    println("Part 1 result: ${puzzle.solvePartOne()}")
    println("Part 2 result: ${puzzle.solvePartTwo()}")
}

public fun printBenchmarkedResult(block: () -> Puzzle<*, *>) {
    val puzzle = block()
    println("${puzzle.day} December ${puzzle.year} (input - ${puzzle.inputData.displayName}):")

    val (partOne, partOneDuration) = measureTimedValue(puzzle::solvePartOne)
    println("Part 1 result: $partOne (took $partOneDuration)")

    val (partTwo, partTwoDuration) = measureTimedValue(puzzle::solvePartTwo)
    println("Part 2 result: $partTwo (took $partTwoDuration)")

    println("Total duration: ${partOneDuration + partTwoDuration}")
}
