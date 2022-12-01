package net.olegg.aoc.year2022.day1

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 1](https://adventofcode.com/2022/day/1)
 */
object Day1 : DayOf2022(1) {
  override fun first(): Any? {
    return data.split("\n\n")
      .map { it.parseInts("\n") }
      .maxOf { it.sum() }
  }
}

fun main() = SomeDay.mainify(Day1)
