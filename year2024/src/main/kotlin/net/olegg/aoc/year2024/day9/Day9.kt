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
    val row = IntArray(length)

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

    return row.takeWhile { it != Int.MAX_VALUE }
      .mapIndexed { index, value -> index * value.toLong() }
      .sum()
  }
}

fun main() = SomeDay.mainify(Day9)
