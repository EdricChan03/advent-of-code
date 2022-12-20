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

/** Checks if the specified [List] contains only unique elements. */
val <E> List<E>.isUnique get() = distinct().size == size

/** Checks if the specified [List] contains only unique elements as returned by [selector]. */
fun <E, K> List<E>.isUniqueBy(selector: (E) -> K) = distinctBy(selector).size == size

/** Returns the number of boolean elements that are the value `true`. */
fun List<Boolean>.countTrue() = count { it }

/** Returns the number of boolean elements that are the value `false`. */
fun List<Boolean>.countFalse() = count { !it }


/**
 * Returns a list containing the first (and last) elements satisfying the given [predicate].
 */
inline fun <T> Iterable<T>.takeUntil(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (item in this) {
        list.add(item)
        // Perform the checking _after_ the item has been added
        if (!predicate(item))
            break
    }
    return list
}
