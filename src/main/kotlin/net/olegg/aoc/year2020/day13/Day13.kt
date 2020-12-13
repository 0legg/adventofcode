package net.olegg.aoc.year2020.day113

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 13](https://adventofcode.com/2020/day/13)
 */
object Day13 : DayOf2020(13) {
  override fun first(data: String): Any? {
    val items = data
      .trim()
      .lines()

    return items
  }
}

fun main() = SomeDay.mainify(Day13)
