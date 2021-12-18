package com.edricchan.aoc

/**
 * Provides the test data for a specific [Puzzle].
 * @property puzzleMeta Metadata for the puzzle.
 * @property puzzle A function that returns the puzzle.
 * @property part1Ans The correct answer(s) for part 1.
 * @property part2Ans The correct answer(s) for part 2.
 * @param Input Any optional test inputs to be supplied to the puzzle.
 * @param PartOneOutput The type to use for part 1's output.
 * @param PartTwoOutput The type to use for part 2's output.
 */
data class PuzzleTestData<Input, PartOneOutput, PartTwoOutput>(
    val puzzleMeta: PuzzleMeta,
    val puzzle: (Input?) -> Puzzle<PartOneOutput, PartTwoOutput>,
    val part1Ans: List<Answer<Input, PartOneOutput>>,
    val part2Ans: List<Answer<Input, PartTwoOutput>>
) {
    /**
     * An answer to be used for the puzzle.
     * @property input The input to use.
     * @property expectedOutput The expected output given the [input].
     * @property testName The test name.
     */
    data class Answer<Input, Output>(
        val input: Input?,
        val expectedOutput: Output,
        val testName: String = getTestName(input, expectedOutput)
    ) {
        companion object {
            /** The default test name function to use. */
            fun <Input, Output> getTestName(input: Input?, expectedOutput: Output): String {
                return if (input != null) "given $input should produce the correct output $expectedOutput"
                else "should produce the correct output $expectedOutput"
            }
        }
    }

    companion object {
        /** The default input to be used. */
        val NO_INPUT = null
    }
}

class PuzzleTestDSL(
    private val year: Int,
    private val puzzlesList: MutableList<PuzzleTestData<*, *, *>> = mutableListOf()
) {
    fun <PartOneOutput, PartTwoOutput> puzzle(puzzle: PuzzleTestData<*, PartOneOutput, PartTwoOutput>) {
        puzzlesList += puzzle
    }

    fun <PartOneOutput, PartTwoOutput> puzzle(
        day: Int,
        puzzle: Puzzle<PartOneOutput, PartTwoOutput>,
        part1Ans: PartOneOutput,
        part2Ans: PartTwoOutput
    ) {
        puzzlesList += PuzzleTestData(
            PuzzleMeta(year, day),
            { puzzle },
            listOf(PuzzleTestData.Answer(PuzzleTestData.NO_INPUT, part1Ans)),
            listOf(PuzzleTestData.Answer(PuzzleTestData.NO_INPUT, part2Ans))
        )
    }

    fun <Input, PartOneOutput, PartTwoOutput> puzzle(
        day: Int,
        puzzle: (Input?) -> Puzzle<PartOneOutput, PartTwoOutput>,
        part1Ans: List<Pair<Input, PartOneOutput>>,
        part2Ans: List<Pair<Input, PartTwoOutput>>
    ) {
        puzzlesList += PuzzleTestData(
            PuzzleMeta(year, day),
            puzzle,
            part1Ans.map { PuzzleTestData.Answer(it.first, it.second) },
            part2Ans.map { PuzzleTestData.Answer(it.first, it.second) }
        )
    }

    @JvmName("puzzleNonPairAnswers")
    fun <Input, PartOneOutput, PartTwoOutput> puzzle(
        day: Int,
        puzzle: (Input?) -> Puzzle<PartOneOutput, PartTwoOutput>,
        part2Ans: List<PuzzleTestData.Answer<Input, PartTwoOutput>>,
        part1Ans: List<PuzzleTestData.Answer<Input, PartOneOutput>>
    ) {
        puzzlesList += PuzzleTestData(
            PuzzleMeta(year, day),
            puzzle,
            part1Ans,
            part2Ans
        )
    }

    fun getPuzzles() = puzzlesList.toList()
}

fun puzzles(year: Int, block: PuzzleTestDSL.() -> Unit): List<PuzzleTestData<*, *, *>> {
    return PuzzleTestDSL(year).apply(block).getPuzzles()
}
