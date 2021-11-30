package net.olegg.aoc.year2018.day8

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 8](https://adventofcode.com/2018/day/8)
 */
object Day8 : DayOf2018(8) {
  override fun first(data: String): Any? {
    val numbers = data
      .trim()
      .parseInts()

    return sumMetadata(ArrayDeque(numbers))
  }

  override fun second(data: String): Any? {
    val numbers = data
      .trim()
      .parseInts()

    return sumValues(ArrayDeque(numbers))
  }

  private fun sumMetadata(data: ArrayDeque<Int>): Int {
    val child = data.removeFirst()
    val metadata = data.removeFirst()

    return (0 until child).sumOf { sumMetadata(data) } + (0 until metadata).sumOf { data.removeFirst() }
  }

  private fun sumValues(data: ArrayDeque<Int>): Int {
    val child = data.removeFirst()
    val metadata = data.removeFirst()

    val childValues = (0 until child)
      .map { it to sumValues(data) }
      .toMap()

    return (0 until metadata).sumOf {
      if (child > 0) {
        childValues[data.removeFirst() - 1] ?: 0
      } else {
        data.removeFirst()
      }
    }
  }
}

fun main() = SomeDay.mainify(Day8)
