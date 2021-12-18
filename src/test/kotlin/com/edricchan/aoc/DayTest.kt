package com.edricchan.aoc

import io.kotest.core.spec.style.describeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

/**
 * Creates a test factory given the puzzle and answers as supplied by [PuzzleTestData].
 * @param testData The test data.
 */
fun <Input, PartOneOutput, PartTwoOutput> puzzleTests(
    testData: PuzzleTestData<Input, PartOneOutput, PartTwoOutput>
) = describeSpec {
    val (_, puzzleFn, part1Ans, part2Ans) = testData

    describe("Day ${testData.puzzleMeta.day}") {
        describe("part one") {
            withData(nameFn = { it.testName }, part1Ans) { ans ->
                val puzzle = puzzleFn(ans.input)
                val output = puzzle.solvePartOne()
                output shouldBe ans.expectedOutput
            }
        }

        describe("part two") {
            withData(nameFn = { it.testName }, part2Ans) { ans ->
                val puzzle = puzzleFn(ans.input)
                val output = puzzle.solvePartTwo()
                output shouldBe ans.expectedOutput
            }
        }
    }
}
