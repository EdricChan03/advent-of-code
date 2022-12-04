package com.edricchan.aoc.utils

/** Checks if the receiver [IntRange] fully overlaps (or "contains") with [other]. */
infix fun IntRange.contains(other: IntRange) =
    // Receiver range contains other range
    (first <= other.first && other.last <= last) ||
    // Other range contains receiver range
    (other.first <= first && last <= other.last)

/** Checks if the receiver [IntRange] overlaps with [other]. */
infix fun IntRange.overlaps(other: IntRange) =
    first <= other.last && other.first <= last
/**
 * Checks if there are any full overlaps in the given [Pair] of [IntRange]s.
 */
val Pair<IntRange, IntRange>.hasFullOverlap get() = first contains second

/**
 * Checks if there are any partial overlaps in the given [Pair] of [IntRange]s.
 */
val Pair<IntRange, IntRange>.hasPartialOverlap get() = first overlaps second
