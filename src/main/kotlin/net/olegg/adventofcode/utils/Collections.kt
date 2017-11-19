package net.olegg.adventofcode.utils

import kotlin.coroutines.experimental.buildSequence

/**
 * Extension functions and utility methods for collections.
 */

/**
 * Accumulates value starting with [initial] value and applying [operation] from left to right to current accumulator value and each element.
 */
inline fun <T, R> Iterable<T>.scan(initial: R, operation: (R, T) -> R): List<R> {
    var accumulator = initial
    var list = listOf<R>()
    for (element in this) {
        accumulator = operation(accumulator, element)
        list += accumulator
    }
    return list
}

/**
 * Accumulates value starting with [initial] value and applying [operation] from left to right to current accumulator value and each element.
 */
inline fun <T, R> Sequence<T>.scan(initial: R, operation: (R, T) -> R): List<R> {
    var accumulator = initial
    var list = listOf<R>()
    for (element in this) {
        accumulator = operation(accumulator, element)
        list += accumulator
    }
    return list
}

/**
 * Generates the sequence of all permutations of items in current list.
 */
fun <T : Any> List<T>.permutations(): Sequence<List<T>> = if (size == 1) sequenceOf(this) else {
    val iterator = iterator()
    var head = iterator.next()
    var permutations = (this - head).permutations().iterator()

    fun nextPermutation(): List<T>? = if (permutations.hasNext()) listOf(head) + permutations.next() else {
        if (iterator.hasNext()) {
            head = iterator.next()
            permutations = (this - head).permutations().iterator()
            nextPermutation()
        } else null
    }

    generateSequence { nextPermutation() }
}

/**
 * Splits iterable into list of subsequences such that each subsequence contains only equal items.
 */
fun <T> Iterable<T>.series(): List<List<T>> {
    val list = arrayListOf<ArrayList<T>>()
    var store = arrayListOf<T>()
    for (element in this) {
        if (store.contains(element)) {
            store.add(element)
        } else {
            store = arrayListOf(element)
            list.add(store)
        }
    }

    return list
}

inline fun <T> Sequence<T>.chunks(size: Int): Sequence<List<T>> {
    val iter = this.iterator()
    return buildSequence {
        var item = mutableListOf<T>()
        while (iter.hasNext()) {
            item.add(iter.next())
            if (item.size == size) {
                yield(item)
                item = mutableListOf<T>()
            }
        }
        if (item.isNotEmpty()) {
            yield(item)
        }
    }
}
