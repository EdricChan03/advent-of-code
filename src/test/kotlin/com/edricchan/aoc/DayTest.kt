package com.edricchan.aoc

import io.kotest.core.spec.style.describeSpec
import io.kotest.matchers.shouldBe

/**
 * Creates a test factory given the puzzle and answers as supplied by [PuzzleTestData].
 * @param puzzleTestData The test data.
 */
fun <PartOneOutput, PartTwoOutput> puzzleTests(
    puzzleTestData: PuzzleTestData<PartOneOutput, PartTwoOutput>
) = describeSpec {
    val (puzzle, part1Ans, part2Ans) = puzzleTestData

    describe("part one") {
        it("should produce the correct output") {
            val output = puzzle.solvePartOne()
            output shouldBe part1Ans
        }
    }

    describe("part two") {
        it("should produce the correct output") {
            val output = puzzle.solvePartTwo()
            output shouldBe part2Ans
        }
    }
}
