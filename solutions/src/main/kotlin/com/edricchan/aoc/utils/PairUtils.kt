package com.edricchan.aoc.utils

/**
 * Returns the results of applying the given [transform] function to the
 * original [Pair].
 */
fun <T, R> Pair<T, T>.map(transform: (Pair<T, T>) -> R): R {
    return transform(this)
}

/**
 * Returns the results of applying the given [transform] function to the
 * original [Pair].
 */
@JvmName("mapWithTwoGenerics")
fun <T1, T2, R> Pair<T1, T2>.map(transform: (Pair<T1, T2>) -> R): R {
    return transform(this)
}

/**
 * Returns the results of applying the given [transform] function to each
 * element in the original [Pair].
 */
fun <T, R> Pair<T, T>.mapValues(transform: (T) -> R): Pair<R, R> {
    return transform(first) to transform(second)
}
