package net.olegg.aoc.year2024.day9

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 9](https://adventofcode.com/2024/day/9)
 */
object Day9 : DayOf2024(9) {
  override fun first(): Any? {
    val sizes = data.map { it.digitToInt() }
    val length = sizes.sum()
    val row = IntArray(length) { Int.MAX_VALUE }

    val chunks = sizes
      .mapIndexed { index, size ->
        size to if (index % 2 == 0) index / 2 else Int.MAX_VALUE
      }

    val starts = chunks.scan(0) { acc, (size, _) -> acc + size }.dropLast(1)

    starts
      .zip(chunks) { start, (size, value) ->
        Triple(start, size, value)
      }
      .forEach { (start, size, value) ->
        row.fill(value, start, start + size)
      }

    var p1 = 0
    var p2 = row.lastIndex
    while (p1 < p2) {
      while (row[p1] != Int.MAX_VALUE && p1 < p2) {
        p1++
      }
      while (row[p2] == Int.MAX_VALUE && p1 < p2) {
        p2--
      }
      if (p1 < p2) {
        row[p1] = row[p2]
        row[p2] = Int.MAX_VALUE
        p1++
        p2--
      }
    }

    return row
      .mapIndexed { index, value -> if (value != Int.MAX_VALUE) index * value.toLong() else 0 }
      .sum()
  }

  override fun second(): Any? {
    val sizes = data.map { it.digitToInt() }
    val length = sizes.sum()
    val row = IntArray(length) { Int.MAX_VALUE }

    val chunks = sizes
      .mapIndexed { index, size ->
        size to if (index % 2 == 0) index / 2 else Int.MAX_VALUE
      }

    val starts = chunks.scan(0) { acc, (size, _) -> acc + size }.dropLast(1)

    val map = starts
      .zip(chunks) { start, (size, value) ->
        Triple(start, size, value)
      }
      .toTypedArray()

    for (curr in map.lastIndex downTo 0 step 2) {
      val (itemStart, itemSize, itemValue) = map[curr]
      val slot = map.indexOfFirst { (start, size, value) ->
        value == Int.MAX_VALUE && start < itemStart && size >= itemSize
      }

      if (slot != -1) {
        val (start, size, _) = map[slot]
        map[curr] = Triple(start, itemSize, itemValue)
        map[slot] = map[slot].copy(first = start + itemSize, second = size - itemSize)
      }
    }

    map.forEach { (start, size, value) ->
      row.fill(value, start, start + size)
    }

    return row
      .mapIndexed { index, value -> if (value != Int.MAX_VALUE) index * value.toLong() else 0 }
      .sum()
  }
}

fun main() = SomeDay.mainify(Day9)
