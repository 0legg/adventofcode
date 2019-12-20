package net.olegg.aoc.utils

/**
 * Extension functions and utility methods for collections.
 */

/**
 * Accumulates value starting with [initial] value and applying [operation] from left to right
 * to current accumulator value and each element.
 */
inline fun <T, R> Iterable<T>.scan(initial: R, operation: (R, T) -> R): List<R> {
  var accumulator = initial
  val list = mutableListOf<R>()
  for (element in this) {
    accumulator = operation(accumulator, element)
    list += accumulator
  }
  return list
}

/**
 * Accumulates value starting with [initial] value and applying [operation] from left to right
 * to current accumulator value and each element.
 */
inline fun <T, R> Sequence<T>.scan(initial: R, crossinline operation: (R, T) -> R): Sequence<R> {
  return sequence {
    var accumulator = initial
    for (element in this@scan) {
      accumulator = operation(accumulator, element)
      yield(accumulator)
    }
  }
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

fun <T : Any> List<T>.pairs(): Sequence<Pair<T, T>> {
  require(size > 1)
  return sequence {
    for (x in indices) {
      for (y in x + 1 until size) {
        yield(get(x) to get(y))
      }
    }
  }
}

/**
 * Splits iterable into list of subsequences such that each subsequence contains only equal items.
 */
fun <T> Iterable<T>.series(): List<List<T>> {
  val list = mutableListOf<MutableList<T>>()
  var store = mutableListOf<T>()
  for (element in this) {
    if (store.contains(element)) {
      store.add(element)
    } else {
      store = mutableListOf(element)
      list.add(store)
    }
  }

  return list
}

/**
 * Finds first entry equal to [value] in matrix
 */
fun <T> List<List<T>>.find(value: T): Vector2D? {
  forEachIndexed { y, row ->
    row.forEachIndexed { x, curr ->
      if (curr == value) {
        return Vector2D(x, y)
      }
    }
  }
  return null
}

operator fun <T> List<MutableList<T>>.set(v: Vector2D, value: T) {
  this[v.y][v.x] = value
}

operator fun <T> List<MutableList<T>>.set(i: Int, j: Int, value: T) {
  this[i][j] = value
}

operator fun <T> List<List<T>>.get(v: Vector2D): T {
  return this[v.y][v.x]
}
