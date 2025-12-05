package net.olegg.aoc.year2025.day5

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2025.DayOf2025

/**
 * See [Year 2025, Day 5](https://adventofcode.com/2025/day/5)
 */
object Day5 : DayOf2025(5) {
  override fun first(): Any? {
    val (rawRanges, rawElements) = data.split("\n\n")

    val ranges = rawRanges
      .lines()
      .map { it.parseLongs("-") }
      .map { it.first()..it.last() }

    val elements = rawElements.parseLongs("\n")

    return elements.count { element ->
      ranges.any { element in it }
    }
  }
}

fun main() = SomeDay.mainify(Day5)
