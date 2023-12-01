package net.olegg.aoc.year2023.day1

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 1](https://adventofcode.com/2023/day/1)
 */
object Day1 : DayOf2023(1) {
  override fun first(): Any? {
    return lines
      .map { line ->
        line.filter { it.isDigit() }
      }
      .sumOf { "${it.first()}${it.last()}".toInt() }
  }
}

fun main() = SomeDay.mainify(Day1)
