package com.edricchan.aoc.utils

/** Checks if the receiver [IntRange] fully overlaps (or "contains") with [other]. */
infix fun IntRange.contains(other: IntRange) =
    other.all { it in this } || all { it in other }

/** Checks if the receiver [IntRange] overlaps with [other]. */
infix fun IntRange.overlaps(other: IntRange) =
    other.any { it in this } || any { it in other }

/**
 * Checks if there are any full overlaps in the given [Pair] of [IntRange]s.
 */
val Pair<IntRange, IntRange>.hasFullOverlap get() = first contains second

/**
 * Checks if there are any partial overlaps in the given [Pair] of [IntRange]s.
 */
val Pair<IntRange, IntRange>.hasPartialOverlap get() = first overlaps second
