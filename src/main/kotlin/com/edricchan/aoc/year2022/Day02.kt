package com.edricchan.aoc.year2022

import com.edricchan.aoc.Puzzle
import com.edricchan.aoc.solve
import com.edricchan.aoc.year2022.day02.RPSResult
import com.edricchan.aoc.year2022.day02.RPSShape
import com.edricchan.aoc.year2022.day02.calcScore
import com.edricchan.aoc.year2022.day02.plays

fun main() = solve { Day02() }

private data class Part1RPSLetter(
    val letters: List<String>,
    val shape: RPSShape
)

class Day02 : Puzzle<Int, Int>(year = 2022, day = 2) {
    override fun solvePartOne(): Int {
        val letterMap = listOf(
            Part1RPSLetter(listOf("a", "x"), RPSShape.Rock),
            Part1RPSLetter(listOf("b", "y"), RPSShape.Paper),
            Part1RPSLetter(listOf("c", "z"), RPSShape.Scissors)
        )

        val sumRounds = useInput { lines ->
            lines.flatMap { round ->
                round.splitToSequence(" ")
                    .map { inputLetter ->
                        letterMap
                            .first { inputLetter.lowercase() in it.letters }.shape
                    }
                    .zipWithNext()
            }
                .sumOf { (opp, cur) ->
                    (cur plays opp).score + cur.score
                }
        }
        return sumRounds
    }

    override fun solvePartTwo(): Int {
        val letterMap = mapOf(
            "a" to RPSShape.Rock,
            "b" to RPSShape.Paper,
            "c" to RPSShape.Scissors
        )
        val resultMap = mapOf(
            "x" to RPSResult.Lose,
            "y" to RPSResult.Draw,
            "z" to RPSResult.Win
        )

        val sumRounds = useInput { lines ->
            lines.flatMap { round ->
                round.splitToSequence(" ").zipWithNext()
                    .map { (first, second) ->
                        letterMap.getValue(first.lowercase()) to resultMap.getValue(second.lowercase())
                    }
            }
                .sumOf { (shape, result) ->
                    shape.calcScore(result)
                }
        }
        return sumRounds
    }
}
