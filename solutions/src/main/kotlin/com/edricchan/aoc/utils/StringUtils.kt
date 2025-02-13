package com.edricchan.aoc.utils

// Probably better solution:
//fun String.splitEvenHalf2() = substring(0, length / 2) to substring(length / 2)

/**
 * Splits a string into half. This method assumes that the string
 * has an even number of characters.
 */
fun String.splitEvenHalf() = chunked(length / 2).let { it[0] to it[1] }

/**
 * Splits a string into half, and transforms both values using the given
 * [transform] lambda. This method assumes that the string has an even
 * number of characters.
 * @param transform The lambda used to transform each value with, where each value
 * is passed as an argument.
 */
fun <T> String.splitEvenHalf(
    transform: (String) -> T
) = chunked(length / 2).let { transform(it[0]) to transform(it[1]) }

/**
 * Splits a string into half, and transforms both values using the given
 * [transform] lambda. This method assumes that the string has an even
 * number of characters.
 * @param transform The lambda used to transform each value with, where each
 * value and its index are passed as arguments.
 */
fun <T> String.splitEvenHalf(
    transform: (String, Int) -> T
) = chunked(length / 2).let { transform(it[0], 0) to transform(it[1], 1) }

/** Splits the receiver [String] by whitespaces (`\s+`). */
fun String.splitWhitespace() = split(Regex("""\s+"""))

/**
 * Splits a string by the [delimiter], and [transform]s the results into a resulting [Pair]
 * of values.
 *
 * For example:
 * ```kotlin
 * "123|456".splitPair("|") { it.toInt() } // (123, 456)
 * ```
 */
fun <T> String.splitPair(
    delimiter: String,
    transform: (String) -> T
): Pair<T, T> {
    val (a, b) = split(delimiter)
    return transform(a) to transform(b)
}

/**
 * Splits a string by the [delimiter], and transforms the results into a resulting [Pair]
 * of values.
 *
 * For example:
 * ```kotlin
 * "123|false".splitPair("|", transformLeft = { it.toInt() }, transformRight = { it.toBooleanStrict() }) // (123, true)
 * ```
 */
fun <TLeft, TRight> String.splitPair(
    delimiter: String,
    transformLeft: (String) -> TLeft,
    transformRight: (String) -> TRight
): Pair<TLeft, TRight> {
    val (a, b) = split(delimiter)
    return transformLeft(a) to transformRight(b)
}

/**
 * Splits a string by the [delimiter] into a resulting [Pair] of values.
 *
 * For example:
 * ```kotlin
 * "123|456".splitPair("|") // ("123", "456")
 * ```
 */
fun String.splitPair(delimiter: String): Pair<String, String> = splitPair(delimiter) { it }
