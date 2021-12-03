package com.edricchan.aoc

/**
 * Provides the test data for a specific [Puzzle].
 * @property puzzle The [Puzzle].
 * @property part1Ans The correct answer for part 1.
 * @property part2Ans The correct answer for part 2.
 * @param PartOneOutput The type to use for part 1's output.
 * @param PartTwoOutput The type to use for part 2's output.
 */
data class PuzzleTestData<PartOneOutput, PartTwoOutput>(
    val puzzle: Puzzle<PartOneOutput, PartTwoOutput>,
    val part1Ans: PartOneOutput,
    val part2Ans: PartTwoOutput
)
