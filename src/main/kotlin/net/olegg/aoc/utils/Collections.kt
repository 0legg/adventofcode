package net.olegg.aoc.utils

/**
 * Extension functions and utility methods for collections.
 */

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
  when {
    v.y !in indices -> throw IndexOutOfBoundsException()
    v.x !in this[v.y].indices -> throw IndexOutOfBoundsException()
    else -> this[v.y][v.x] = value
  }
}

operator fun <T> List<MutableList<T>>.set(i: Int, j: Int, value: T) {
  this[i][j] = value
}

operator fun <T> List<List<T>>.get(v: Vector2D): T? = when {
  v.y !in indices -> null
  v.x !in this[v.y].indices -> null
  else -> this[v.y][v.x]
}

fun <T> List<List<T>>.fit(v: Vector2D) = v.y in indices && v.x in this[v.y].indices
