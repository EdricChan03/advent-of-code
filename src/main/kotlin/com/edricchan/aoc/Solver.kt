package com.edricchan.aoc

import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.io.path.useLines
import kotlin.time.measureTimedValue

/** A resource loader used to retrieve resources. */
fun interface ResourceLoader {
    /** Retrieves the specified resource given the [file name][fileName]. */
    fun getResource(fileName: String): File?

    /** Retrieves the specified resource given the [file name][fileName] as a [Path]. */
    fun getResourceAsPath(fileName: String): Path? = getResource(fileName)?.toPath()

    /**
     * Retrieves the specified resource given the [file name][fileName].
     *
     * If `null` is returned, a runtime [IllegalArgumentException] is thrown.
     * @see requireNotNull
     * @see getResource
     */
    fun requireResource(fileName: String): File = requireNotNull(getResource(fileName)) {
        "Resource with file name $fileName does not exist"
    }

    /**
     * Retrieves the specified resource given the [file name][fileName] as a [Path].
     *
     * If `null` is returned, a runtime [IllegalArgumentException] is thrown.
     * @see requireNotNull
     * @see getResource
     */
    fun requireResourceAsPath(fileName: String): Path = requireNotNull(getResourceAsPath(fileName)) {
        "Resource with file name $fileName does not exist"
    }

    /** The default resource loader. */
    object Default : ResourceLoader {
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
fun getInputFilePath(
    year: Int, day: Int, fileName: String = "input.txt"
) = "aoc/year$year/day$day/$fileName"

/**
 * Retrieves the file path for the given [meta], optionally allowing
 * for the [fileName] to be specified.
 *
 * This is equivalent to the string `"aoc/year${meta.year}/day${meta.day}/$fileName"`.
 *
 * To get the input as a [File], use [getInputFile].
 * To get the input as a [Path], use [getInputPath].
 * @receiver The puzzle's metadata, consisting of [PuzzleMeta.year] and [PuzzleMeta.day].
 * @param fileName The text file's name.
 */
fun PuzzleMeta.getInputFilePath(
    fileName: String = "input.txt"
) = getInputFilePath(year = year.value, day = day, fileName = fileName)

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
fun getInputFile(
    year: Int, day: Int, fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
) = resourceLoader.getResource(getInputFilePath(year, day, fileName))
    ?: throw FileNotFoundException("Input file for year $year, day $day does not exist")

/**
 * Retrieves the input text file for a given [meta] as a [File].
 * @receiver The puzzle metadata, consisting of [PuzzleMeta.year] and [PuzzleMeta.day].
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @return The input text file as a [File] reference.
 * @see getInputPath
 * @see getInputFilePath
 */
fun PuzzleMeta.getInputFile(
    fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
) = getInputFile(
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
fun getInputPath(
    year: Int, day: Int, fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
) = resourceLoader.getResourceAsPath(getInputFilePath(year, day, fileName))
    ?: throw FileNotFoundException("Input file for year $year, day $day does not exist")

/**
 * Retrieves the input text file for a given [meta] as a [Path].
 * @receiver The puzzle's metadata, consisting of [PuzzleMeta.year] and [PuzzleMeta.day].
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @return The input text file as a [Path] reference.
 * @see getInputFile
 * @see getInputFilePath
 */
fun PuzzleMeta.getInputPath(
    fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
) = getInputPath(
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
fun getInput(
    year: Int, day: Int, fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
) = getInputPath(year, day, fileName, resourceLoader).readLines()

/**
 * Retrieves the input text file's contents for a given [meta].
 * @receiver The puzzle metadata, consisting of [PuzzleMeta.year] and [PuzzleMeta.day].
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @return The contents of the text file as a list split by new lines.
 */
fun PuzzleMeta.getInput(
    fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
) = getInput(
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
fun <T> useInput(
    year: Int, day: Int, fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default,
    block: (Sequence<String>) -> T
) = getInputPath(year, day, fileName, resourceLoader).useLines(block = block)

/**
 * Retrieves the input text file's contents for a given [meta] as a [Sequence].
 * @receiver The puzzle metadata, consisting of [PuzzleMeta.year] and [PuzzleMeta.day].
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @param block A callback where the contents of the file are passed as a [Sequence].
 * @return The result of the [block].
 */
fun <T> PuzzleMeta.useInput(
    fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default,
    block: (Sequence<String>) -> T
) = useInput(
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
fun getRawInput(
    year: Int, day: Int, fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
) = getInputPath(year, day, fileName, resourceLoader).readText()

/**
 * Retrieves the input text file's contents for a given [this@getRawInput].
 * @receiver The puzzle metadata, consisting of [PuzzleMeta.year] and [PuzzleMeta.day].
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @return The contents of the text file as a [String].
 */
fun PuzzleMeta.getRawInput(
    fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
) = getRawInput(
    year = year.value, day = day, fileName = fileName, resourceLoader = resourceLoader
)

/**
 * Solves the specified puzzle.
 * @param benchmark Whether to perform benchmarks on the solution.
 * @param block The higher order function to be used to retrieve the [Puzzle].
 * @see printResult
 * @see printBenchmarkedResult
 */
fun solve(benchmark: Boolean = true, block: () -> Puzzle<*, *>) {
    if (benchmark) printBenchmarkedResult(block)
    else printResult(block = block)
}

fun printResult(block: () -> Puzzle<*, *>) {
    val puzzle = block()
    println("${puzzle.day} December ${puzzle.year}:")
    println("Part 1 result: ${puzzle.solvePartOne()}")
    println("Part 2 result: ${puzzle.solvePartTwo()}")
}

fun printBenchmarkedResult(block: () -> Puzzle<*, *>) {
    val puzzle = block()
    println("${puzzle.day} December ${puzzle.year}:")

    val (partOne, partOneDuration) = measureTimedValue(puzzle::solvePartOne)
    println("Part 1 result: $partOne (took $partOneDuration)")

    val (partTwo, partTwoDuration) = measureTimedValue(puzzle::solvePartTwo)
    println("Part 2 result: $partTwo (took $partTwoDuration)")

    println("Total duration: ${partOneDuration + partTwoDuration}")
}
