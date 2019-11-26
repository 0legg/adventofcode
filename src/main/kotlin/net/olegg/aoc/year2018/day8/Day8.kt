package net.olegg.aoc.year2018.day8

import java.util.ArrayDeque
import java.util.Deque
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 8](https://adventofcode.com/2018/day/8)
 */
object Day8 : DayOf2018(8) {
  override fun first(data: String): Any? {
    val numbers = data
        .trim()
        .split("\\s+".toRegex())
        .map { it.toInt() }

    return sumMetadata(ArrayDeque(numbers))
  }

  override fun second(data: String): Any? {
    val numbers = data
        .trim()
        .split("\\s+".toRegex())
        .map { it.toInt() }

    return sumValues(ArrayDeque(numbers))
  }

  private fun sumMetadata(data: Deque<Int>): Int {
    val child = data.pop()
    val metadata = data.pop()

    return (0 until child).sumBy { sumMetadata(data) } + (0 until metadata).sumBy { data.pop() }
  }

  private fun sumValues(data: Deque<Int>): Int {
    val child = data.pop()
    val metadata = data.pop()

    val childValues = (0 until child)
        .map { it to sumValues(data) }
        .toMap()

    return (0 until metadata).sumBy {
      if (child > 0) {
        childValues[data.pop() - 1] ?: 0
      } else {
        data.pop()
      }
    }
  }
}

fun main() = SomeDay.mainify(Day8)
