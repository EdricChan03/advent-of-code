package com.edricchan.aoc.year2022.day02

enum class RPSShape(val score: Int) {
    Rock(1),
    Paper(2),
    Scissors(3)
}

/**
 * The list of winning outcomes.
 *
 * The data structure is represented by `Pair<Left, Right>`, where `Left` defeats `Right`.
 */
val winOutcomesList = listOf(
    RPSShape.Rock to RPSShape.Scissors,
    RPSShape.Scissors to RPSShape.Paper,
    RPSShape.Paper to RPSShape.Rock
)

/**
 * Retrieves the result, given the receiver and [other]'s shapes.
 * @receiver The left-hand side's shape.
 * @param other The right-hand side's shape.
 */
infix fun RPSShape.plays(other: RPSShape): RPSResult {
    return when {
        this == other -> RPSResult.Draw
        winOutcomesList.any { it == this to other } -> RPSResult.Win
        else -> RPSResult.Lose
    }
}

enum class RPSResult(val score: Int) {
    Lose(0),
    Draw(3),
    Win(6)
}
