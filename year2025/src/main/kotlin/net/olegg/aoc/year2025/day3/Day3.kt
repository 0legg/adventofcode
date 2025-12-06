package net.olegg.aoc.year2025.day3

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2025.DayOf2025

/**
 * See [Year 2025, Day 3](https://adventofcode.com/2025/day/3)
 */
object Day3 : DayOf2025(3) {
  override fun first(): Any? {
    return lines.sumOf { joltage(it, 2) }
  }

  override fun second(): Any? {
    return lines.sumOf { joltage(it, 12) }
  }

  private fun joltage(line: String, size: Int): Long {
    val range = 0..size
    val best = line.fold(mapOf(0 to 0L)) { acc, char ->
      val digit = char.digitToInt()
      val next = acc.map { it.key + 1 to it.value * 10 + digit }.toMap()

      range.associateWith {
        maxOf(acc[it] ?: 0, next[it] ?: 0)
      }
    }

    return best.getOrDefault(size, 0)
  }
}

fun main() = SomeDay.mainify(Day3)
