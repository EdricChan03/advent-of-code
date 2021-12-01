package com.edricchan.aoc

/**
 * Retrieves the input text file for a given [day] and [year].
 * @param year The year of the puzzle.
 * @param day The day of the puzzle.
 */
fun getInput(year: Int, day: Int, fileName: String = "input.txt") =
    Puzzle::class.java.classLoader.getResourceAsStream("aoc/year$year/day$day/input.txt")
        .bufferedReader()
        .readLines()

/**
 * Solves the specified puzzle.
 * @param benchmark Whether to perform benchmarks on the solution.
 * @param block The higher order function to be used to retrieve the [Puzzle].
 */
// TODO: Add benchmarking support
fun solve(benchmark: Boolean = true, block: () -> Puzzle<*,*>) {

}

fun printResult(block: () -> Puzzle<*, *>) {
    val puzzle = block()
    println("${puzzle.day} December ${puzzle.year}:")
    println("Part 1 result: ${puzzle.solvePartOne()}")
    println("Part 2 result: ${puzzle.solvePartTwo()}")
}
