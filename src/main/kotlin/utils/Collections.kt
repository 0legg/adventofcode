package utils

/**
 * Created by olegg on 12/18/15.
 */

/**
 * Accumulates value starting with [initial] value and applying [operation] from left to right to current accumulator value and each element.
 */
public inline fun <T, R> Iterable<T>.scan(initial: R, operation: (R, T) -> R): List<R> {
    var accumulator = initial
    var list = listOf<R>()
    for (element in this) {
        accumulator = operation(accumulator, element)
        list += accumulator
    }
    return list
}