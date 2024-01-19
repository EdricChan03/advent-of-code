package com.edricchan.aoc.puzzle.dsl

import com.edricchan.aoc.*
import com.edricchan.aoc.puzzle.PuzzleTestData
import kotlin.properties.Delegates

data class Answer<Input, Output>(val input: Input, val output: Output) {
    fun toTestAnswer() = PuzzleTestData.Answer(input, output)
}

@DslMarker
annotation class PuzzleTestAnsDSL

@PuzzleTestAnsDSL
class PuzzleTestAnsScope<Input, PartOneOutput : Any, PartTwoOutput : Any>(
    private var part1Answers: MutableList<Answer<Input, PartOneOutput>> = mutableListOf(),
    private var part2Answers: MutableList<Answer<Input, PartTwoOutput>> = mutableListOf(),
    private var inputs: MutableList<Input> = mutableListOf(),
    val resourceLoader: ResourceLoader
) {
    @PuzzleTestAnsDSL
    class InputScope<Input, PartOneOutput : Any, PartTwoOutput : Any>(
        private val input: Input
    ) {
        /** Sets the answer for part 1 given the [input]. */
        var partOneAns by Delegates.notNull<PartOneOutput>()

        /**
         * Sets the answer for part 1 given a HOF, which is invoked
         * with the [input], and should return the answer to be used.
         */
        fun partOneAns(answerFn: (Input) -> PartOneOutput) {
            partOneAns = answerFn(input)
        }

        /** Sets the answer for part 2 given the [input]. */
        var partTwoAns by Delegates.notNull<PartTwoOutput>()

        /**
         * Sets the answer for part 2 given a HOF, which is invoked
         * with the [input], and should return the answer to be used.
         */
        fun partTwoAns(answerFn: (Input) -> PartTwoOutput) {
            partTwoAns = answerFn(input)
        }

    }

    /** Adds the specified input's answers via `input { }` syntax. */
    operator fun Input.invoke(scope: InputScope<Input, PartOneOutput, PartTwoOutput>.() -> Unit) {
        inputs += this
        val answer = InputScope<Input, PartOneOutput, PartTwoOutput>(this).apply(scope)
        part1Answers += Answer(this, answer.partOneAns)
        part2Answers += Answer(this, answer.partTwoAns)
    }

    /** Adds the specified [input]'s answers via `withInput(input) { }` syntax. */
    fun withInput(input: Input, scope: InputScope<Input, PartOneOutput, PartTwoOutput>.() -> Unit) {
        input(scope)
    }

    /** Adds the specified [input]'s answers via `withInput(input, part1Ans, part2Ans)` syntax. */
    fun withInput(input: Input, part1Ans: PartOneOutput, part2Ans: PartTwoOutput) {
        input {
            partOneAns = part1Ans
            partTwoAns = part2Ans
        }
    }

    /** Adds the specified input's answers via `input answers { }` syntax. */
    infix fun Input.answers(scope: InputScope<Input, PartOneOutput, PartTwoOutput>.() -> Unit) {
        this(scope)
    }

    /** Adds the specified input's answers via `input.answers(part1Ans, part2Ans)` syntax. */
    fun Input.answers(part1Ans: PartOneOutput, part2Ans: PartTwoOutput) {
        this {
            partOneAns = part1Ans
            partTwoAns = part2Ans
        }
    }

    /** Retrieves the answers as a [Pair]. */
    fun getAnswersAsPair() =
        part1Answers.map { it.toTestAnswer() } to part2Answers.map { it.toTestAnswer() }
}

fun <Input, PartOneOutput : Any, PartTwoOutput : Any> PuzzleTestDSL.puzzle(
    day: Int,
    puzzle: (Input?) -> Puzzle<PartOneOutput, PartTwoOutput>,
    outputScope: PuzzleTestAnsScope<Input, PartOneOutput, PartTwoOutput>.() -> Unit
) {
    puzzle(
        day,
        puzzle,
        PuzzleTestAnsScope<Input, PartOneOutput, PartTwoOutput>(
            resourceLoader = resourceLoader
        ).apply(outputScope).getAnswersAsPair()
    )
}
