package com.edricchan.aoc.utils

/** Returns the first two adjacent elements as a [Pair] in this collection. */
fun <T> List<T>.firstZipWithNext() = zipWithNext()[0]
