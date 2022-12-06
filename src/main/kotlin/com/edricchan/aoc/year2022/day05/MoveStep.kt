package com.edricchan.aoc.year2022.day05

/**
 * Represents a move step for day 5.
 * @param amount The amount of crates to move.
 * @param fromStack The stack to move the crates from.
 * @param toStack The stack to move the crates to.
 */
data class MoveStep(
    val amount: Int,
    val fromStack: Int,
    val toStack: Int
)

/** Parses the string as a [MoveStep]. */
fun String.toMoveStep(): MoveStep? {
    val stepRegex = Regex("""move (\d+) from (\d+) to (\d+)""")
    val match = stepRegex.find(this)
    return match?.destructured?.let {
        val (amount, fromStack, toStack) = it
        MoveStep(amount.toInt(), fromStack.toInt(), toStack.toInt())
    }
}
