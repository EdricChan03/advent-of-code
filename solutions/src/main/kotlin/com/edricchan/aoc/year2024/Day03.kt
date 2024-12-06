package com.edricchan.aoc.year2024

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.PuzzleMeta
import com.edricchan.aoc.puzzle.input.PuzzleInput
import com.edricchan.aoc.puzzle.solve
import java.time.Year

fun main() = solve(block = ::Day03)

private val mulRegex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()

// !!!Non-working regex!!!
// Try it out on https://regexr.com/89o2i :P
// This only appears to work on non-PCRE environments :\
// private val mulPart2Regex = """(?:(?<=do\(\).{0,100}?)|(?<!don't\(\).{0,100}?))(?:mul\((\d+),(\d+)\))""".toRegex()

private val dontRegex = """don't\(\)""".toRegex()
private val doRegex = """do\(\)""".toRegex()

private fun MatchResult.toMulPair(): Pair<Int, Int> {
    val (first, second) = destructured
    return first.toInt() to second.toInt()
}

private fun String.mulsSequence(): Sequence<Pair<Int, Int>> =
    mulRegex.findAll(this).map(MatchResult::toMulPair)

private fun String.mulsSequencePart2(): Sequence<Pair<Int, Int>> {
    val dontIndices = dontRegex.findAll(this).map { it.range }.toList()
    val doIndices = doRegex.findAll(this).map { it.range }.toList()

    val validRanges = listOf(0..(dontIndices.firstOrNull()?.first ?: length)) +
        doIndices.map { doIndex ->
            doIndex.last..(dontIndices.find { it.first > doIndex.last }?.first ?: length)
        }

    return mulRegex.findAll(this)
        .filter { result ->
            val index = result.range.first

            validRanges.any { index in it }
        }
        .map(MatchResult::toMulPair)
}

class Day03(inputData: PuzzleInput? = null) : Puzzle<Int, Int>(
    year = 2024, day = 3,
    inputData = inputData ?: PuzzleInput.Default(Meta)
//    inputData = """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))""".toPuzzleInput()
) {
    companion object {
        val Meta = PuzzleMeta(Year.of(2024), 3)
    }

    override fun solvePartOne(): Int = useNotBlankInput { lines ->
        lines.flatMap { it.mulsSequence() }
            .sumOf { it.first * it.second }
    }

    // `\n` is actually part of the "corruption" too... :\
    // See https://www.reddit.com/r/adventofcode/comments/1h5jdoj/day_3_the_line_count_is_fake/
    override fun solvePartTwo(): Int = rawInput.mulsSequencePart2()
        .onEach { println(it) }
        .sumOf { it.first * it.second }
}
