package com.edricchan.aoc.utils

/**
 * Returns the number of boolean elements that are the value `true`.
 *
 * The operation is _terminal_.
 */
fun Sequence<Boolean>.countTrue() = count { it }

/**
 * Returns the number of boolean elements that are the value `false`.
 *
 * The operation is _terminal_.
 */
fun Sequence<Boolean>.countFalse() = count { !it }
