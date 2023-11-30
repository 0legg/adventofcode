package net.olegg.aoc.utils

/**
 * Generates the sequence of all permutations of items in current list.
 */
fun <T : Any> List<T>.permutations(): Sequence<List<T>> = if (size == 1) {
  sequenceOf(this)
} else {
  val iterator = iterator()
  var head = iterator.next()
  var permutations = (this - head).permutations().iterator()

  fun nextPermutation(): List<T>? = if (permutations.hasNext()) {
    listOf(head) + permutations.next()
  } else {
    if (iterator.hasNext()) {
      head = iterator.next()
      permutations = (this - head).permutations().iterator()
      nextPermutation()
    } else {
      null
    }
  }

  generateSequence { nextPermutation() }
}

fun <T : Any> List<T>.pairs(): Sequence<Pair<T, T>> {
  require(size > 1)
  return sequence {
    for (x in indices) {
      for (y in x + 1..<size) {
        yield(get(x) to get(y))
      }
    }
  }
}

/**
 * Splits iterable into run-length encoded list.
 */
fun <T> Iterable<T>.series(): List<Pair<T, Int>> {
  return buildList {
    var curr: T? = null
    var count = 0
    for (element in this@series) {
      if (element == curr) {
        count++
      } else {
        curr?.let { add(it to count) }
        curr = element
        count = 1
      }
    }
    curr?.let { add(it to count) }
  }
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

operator fun <T> List<MutableList<T>>.set(
  v: Vector2D,
  value: T
) {
  when {
    v.y !in indices -> throw IndexOutOfBoundsException("index: ${v.y}.${v.x}")
    v.x !in this[v.y].indices -> throw IndexOutOfBoundsException("index: ${v.y}.${v.x}")
    else -> this[v.y][v.x] = value
  }
}

operator fun <T> List<MutableList<T>>.set(
  i: Int,
  j: Int,
  value: T
) {
  this[i][j] = value
}

operator fun <T> List<List<T>>.get(v: Vector2D): T? = when {
  v.y !in indices -> null
  v.x !in this[v.y].indices -> null
  else -> this[v.y][v.x]
}

fun <T> List<List<T>>.fit(v: Vector2D) = v.y in indices && v.x in this[v.y].indices

fun <T> List<T>.toPair(): Pair<T, T> = first() to last()
