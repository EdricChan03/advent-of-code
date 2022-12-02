package com.edricchan.aoc.year2022.day02

// Code for part 2

/**
 * Retrieves the winning outcome given the [opponent's score][RPSShape].
 * @receiver The opponent's score.
 */
fun RPSShape.findWinningOutcome(): RPSShape {
    return winOutcomesList.first { it.second == this }.first
}

/**
 * Retrieves the losing outcome given the [opponent's score][RPSShape].
 * @receiver The opponent's score.
 */
fun RPSShape.findLosingOutcome(): RPSShape {
    return winOutcomesList.first { it.first == this }.second
}

/**
 * Calculates the score given the [opponent's score][RPSShape] and [result].
 * @receiver The opponent's score.
 */
fun RPSShape.calcScore(result: RPSResult): Int {
    return when (result) {
        // Draw
        RPSResult.Draw -> score
        // Win
        RPSResult.Win -> findWinningOutcome().score
        // Lose
        RPSResult.Lose -> findLosingOutcome().score
    } + result.score
}
