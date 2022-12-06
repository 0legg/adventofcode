package net.olegg.aoc.utils

class UnionFind(size: Int) {
  private val id = IntArray(size) { it }
  private val size = IntArray(size) { 1 }
  var count = size
    private set

  fun root(item: Int): Int {
    var pointer = item
    while (pointer != id[pointer]) {
      pointer = id[pointer]
    }
    val root = pointer
    pointer = item
    while (pointer != id[pointer]) {
      val oldPointer = pointer
      pointer = id[pointer]
      id[oldPointer] = root
    }
    return root
  }

  fun union(a: Int, b: Int) {
    val rootA = root(a)
    val rootB = root(b)
    if (rootA != rootB) {
      if (size[rootA] < size[rootB]) {
        id[rootA] = rootB
        size[rootB] += size[rootA]
      } else {
        id[rootB] = rootA
        size[rootA] += size[rootB]
      }
      count--
    }
  }

  fun find(a: Int, b: Int) = root(a) == root(b)
}
