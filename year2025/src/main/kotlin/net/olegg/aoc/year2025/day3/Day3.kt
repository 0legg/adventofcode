package net.olegg.aoc.year2025.day3

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.pairs
import net.olegg.aoc.year2025.DayOf2025

/**
 * See [Year 2025, Day 3](https://adventofcode.com/2025/day/3)
 */
object Day3 : DayOf2025(3) {
  override fun first(): Any? {
    return lines.sumOf { line ->
      line.toList().pairs().maxOf { "${it.first}${it.second}".toInt() }
    }
  }
}

fun main() = SomeDay.mainify(Day3)
