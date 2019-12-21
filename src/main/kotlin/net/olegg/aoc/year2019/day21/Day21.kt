package net.olegg.aoc.year2019.day21

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2019.DayOf2019

/**
 * See [Year 2019, Day 21](https://adventofcode.com/2019/day/21)
 */
object Day21 : DayOf2019(21) {
  override fun first(data: String): Any? {
    return data
        .trim()
        .lines()
  }
}

fun main() = SomeDay.mainify(Day21)
