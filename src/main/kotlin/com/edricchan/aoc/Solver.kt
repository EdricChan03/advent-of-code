package com.edricchan.aoc

import java.io.FileNotFoundException
import java.io.InputStream

/** A resource loader used to retrieve resources. */
fun interface ResourceLoader {
    /** Retrieves the specified resource given the [file name][fileName]. */
    fun getResource(fileName: String): InputStream?

    /** The default resource loader. */
    object Default : ResourceLoader {
        override fun getResource(fileName: String): InputStream? {
            return this::class.java.getResourceAsStream(fileName)
        }
    }
}

/**
 * Retrieves the input text file for a given [day] and [year].
 * @param year The year of the puzzle.
 * @param day The day of the puzzle.
 * @param fileName The text file's name.
 * @param resourceLoader The resource loader to be used to retrieve the input text file.
 * @return The contents of the text file as a list split by new lines.
 */
fun getInput(
    year: Int, day: Int, fileName: String = "input.txt",
    resourceLoader: ResourceLoader = ResourceLoader.Default
) =
    (resourceLoader.getResource("aoc/year$year/day$day/$fileName")
        ?: throw FileNotFoundException("Input file for $year, day $day does not exist"))
        .bufferedReader()
        .readLines()

/**
 * Solves the specified puzzle.
 * @param benchmark Whether to perform benchmarks on the solution.
 * @param block The higher order function to be used to retrieve the [Puzzle].
 */
// TODO: Add benchmarking support
fun solve(benchmark: Boolean = true, block: () -> Puzzle<*, *>) {
    printResult(block)
}

fun printResult(block: () -> Puzzle<*, *>) {
    val puzzle = block()
    println("${puzzle.day} December ${puzzle.year}:")
    println("Part 1 result: ${puzzle.solvePartOne()}")
    println("Part 2 result: ${puzzle.solvePartTwo()}")
}
