package net.olegg.aoc.year2022.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 6](https://adventofcode.com/2022/day/6)
 */
object Day6 : DayOf2022(6) {

  override fun first(): Any? {
    return data
      .windowed(4)
      .indexOfFirst { it.toSet().size == 4 } + 4
  }
}

fun main() = SomeDay.mainify(Day6)
