package com.edricchan.aoc.year2022.day05

import com.edricchan.aoc.utils.transposed

/** Represents a single [crate]. */
@JvmInline
value class Crate(val crate: Char?) {
    companion object {
        /** Represents an empty crate. */
        val Empty = Crate(null)
    }
}

/** Represents a stack of crates. */
typealias CrateStack = List<Crate>

/** Converts the string to a [Crate], or `null` otherwise. */
fun String.toCrateOrNull(): Crate? {
    val crateRegex = Regex("""\[([A-Z])]""")
    val match = crateRegex.find(this)
    return match?.destructured?.let {
        val (char) = it
        Crate(char.first())
    }
}

/** Converts the string to a [Crate], or [Crate.Empty] if it is not a valid crate. */
fun String.toCrate() = toCrateOrNull() ?: Crate.Empty

/**
 * Maps the specified string to an [ArrayDeque] of [CrateStack]s.
 *
 * Each stack is listed in top-down order.
 */
fun String.toCrateStacks(): List<List<Crate>> {
    return lines().dropLast(1).map { row ->
        val result = row.split(" ".repeat(4), " ").map { it.toCrate() }
        // println("Row: $row, result: $result")
        result
    }.transposed.map { stack ->
        stack.filterNot { it == Crate.Empty }
    }
}
