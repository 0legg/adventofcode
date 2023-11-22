package net.olegg.aoc.year2022.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 6](https://adventofcode.com/2022/day/6)
 */
object Day6 : DayOf2022(6) {
  override fun first(): Any? {
    return solve(4)
  }

  override fun second(): Any? {
    return solve(14)
  }

  private fun solve(length: Int): Int {
    return data
      .windowed(length)
      .indexOfFirst { it.toSet().size == length } + length
  }
}

fun main() = SomeDay.mainify(Day6)
