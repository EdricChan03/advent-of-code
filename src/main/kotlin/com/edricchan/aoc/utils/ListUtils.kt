package com.edricchan.aoc.utils

/** Returns the first two adjacent elements as a [Pair] in this collection. */
fun <E> List<E>.firstZipWithNext() = zipWithNext()[0]

/** Transposes the given matrix-like data structure. */
val <E> List<List<E>>.transposed: List<List<E>>
    get() {
        val cols = this[0].size
        val rows = size
        return List(cols) { j ->
            List(rows) { i ->
                this[i][j]
            }
        }
    }
