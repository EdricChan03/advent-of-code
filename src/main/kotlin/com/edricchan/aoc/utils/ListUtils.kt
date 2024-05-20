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

/** Checks if the receiver [Iterable] has any common elements with the [other] iterable. */
infix fun <T> Iterable<T>.hasCommonElementsWith(other: Iterable<T>): Boolean = other.any { it in toSet() }

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

/**
 * Calculates the products over the list of integers, and returns the result.
 *
 * This is the equivalent of the following expression:
 * ```kotlin
 * fold(1) { acc, element ->
 *     acc * element
 * }
 * ```
 *
 * See [∏](https://en.wiktionary.org/wiki/%E2%88%8F) for more info.
 */
fun Iterable<Int>.product() = fold(1) { acc, element ->
    acc * element
}

/**
 * Calculates the products over the list of integers produced by the
 * [selector] function applied to each element in the collection, and returns the result.
 *
 * This is the equivalent of the following expression:
 * ```kotlin
 * fold(1) { acc, element ->
 *     acc * selector(element)
 * }
 * ```
 *
 * See [∏](https://en.wiktionary.org/wiki/%E2%88%8F) for more info.
 * @param selector Lambda used to retrieve an [Int] from an element [T].
 */
inline fun <T> Iterable<T>.productOf(selector: (T) -> Int) = fold(1) { acc, element ->
    acc * selector(element)
}
