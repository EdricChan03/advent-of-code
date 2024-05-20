// TODO: Move to test-fixtures
package com.edricchan.aoc.puzzle.dsl

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.PuzzleMeta
import com.edricchan.aoc.puzzle.ResourceLoader
import com.edricchan.aoc.puzzle.PuzzleTestData
import java.time.Year

@DslMarker
annotation class PuzzleTestDsl

/**
 * DSL for puzzle tests.
 * @property year The year for this set of puzzles.
 * @property resourceLoader [ResourceLoader] to be used for loading files.
 */
@PuzzleTestDsl
class PuzzleTestDSL(
    val year: Year,
    private val puzzlesList: MutableList<PuzzleTestData<*, *, *>> = mutableListOf(),
    val resourceLoader: ResourceLoader = ResourceLoader.Default
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

    @JvmName("puzzleWithAnsPairs")
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

    fun <Input, PartOneOutput, PartTwoOutput> puzzle(
        day: Int,
        puzzle: (Input?) -> Puzzle<PartOneOutput, PartTwoOutput>,
        inputs: List<Input>,
        part1Ans: List<PartOneOutput>,
        part2Ans: List<PartTwoOutput>
    ) {
        require(inputs.size == part1Ans.size) { "Part 1 answers must have the same size as the inputs" }
        require(inputs.size == part2Ans.size) { "Part 2 answers must have the same size as the inputs" }

        puzzlesList += PuzzleTestData(
            PuzzleMeta(year, day),
            puzzle,
            part1Ans.mapIndexed { index, output -> PuzzleTestData.Answer(inputs[index], output) },
            part2Ans.mapIndexed { index, output -> PuzzleTestData.Answer(inputs[index], output) },
        )
    }

    fun <Input, PartOneOutput, PartTwoOutput> puzzle(
        day: Int,
        puzzle: (Input?) -> Puzzle<PartOneOutput, PartTwoOutput>,
        inputs: List<Input>,
        answers: Pair<List<PartOneOutput>, List<PartTwoOutput>>
    ) {
        val (part1Ans, part2Ans) = answers
        puzzle(day, puzzle, inputs, part1Ans, part2Ans)
    }

    fun <Input, PartOneOutput, PartTwoOutput> puzzle(
        day: Int,
        puzzle: (Input?) -> Puzzle<PartOneOutput, PartTwoOutput>,
        part1Ans: List<PuzzleTestData.Answer<Input, PartOneOutput>>,
        part2Ans: List<PuzzleTestData.Answer<Input, PartTwoOutput>>
    ) {
        puzzlesList += PuzzleTestData(
            PuzzleMeta(year, day),
            puzzle,
            part1Ans,
            part2Ans
        )
    }

    fun <Input, PartOneOutput, PartTwoOutput> puzzle(
        day: Int,
        puzzle: (Input?) -> Puzzle<PartOneOutput, PartTwoOutput>,
        answers: Pair<List<PuzzleTestData.Answer<Input, PartOneOutput>>, List<PuzzleTestData.Answer<Input, PartTwoOutput>>>,
    ) {
        val (part1Ans, part2Ans) = answers
        puzzle(day, puzzle, part1Ans, part2Ans)
    }

    fun getPuzzles() = puzzlesList.toList()
}

/**
 * Creates a list of puzzles for the given [year] using DSL syntax.
 *
 * To retrieve the [ResourceLoader] for loading files, use [PuzzleTestDSL.resourceLoader].
 * @see PuzzleTestDSL
 */
@PuzzleTestDsl
fun puzzles(year: Year, block: PuzzleTestDSL.() -> Unit): List<PuzzleTestData<*, *, *>> {
    return PuzzleTestDSL(year).apply(block).getPuzzles()
}
