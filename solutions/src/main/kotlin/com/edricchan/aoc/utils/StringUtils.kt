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
