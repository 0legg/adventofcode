package net.olegg.aoc.year2024.day3

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 3](https://adventofcode.com/2024/day/3)
 */
object Day3 : DayOf2024(3) {
  private val pattern = """mul\((\d+),(\d+)\)""".toRegex()
  override fun first(): Any? {
    return pattern.findAll(data).sumOf { match ->
      val (a, b) = match.destructured
      a.toLong() * b.toLong()
    }
  }
}

fun main() = SomeDay.mainify(Day3)
