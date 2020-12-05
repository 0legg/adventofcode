package net.olegg.aoc.year2020.day5

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 5](https://adventofcode.com/2020/day/5)
 */
object Day5 : DayOf2020(5) {
  override fun first(data: String): Any? {
    return data
      .trim()
      .lines()
      .asSequence()
      .map { it.replace("F", "0") }
      .map { it.replace("B", "1") }
      .map { it.replace("L", "0") }
      .map { it.replace("R", "1") }
      .map { it.toInt(2) }
      .maxOrNull()
  }
}

fun main() = SomeDay.mainify(Day5)
