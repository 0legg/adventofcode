package net.olegg.aoc.year2015.day5

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 5](https://adventofcode.com/2015/day/5)
 */
object Day5 : DayOf2015(5) {
  val strings = data.trim().lines()
  override fun first(data: String): Any? {
    return strings
        .filter { it.count { it in "aeiou" } >= 3 }
        .filterNot { it.contains("ab|cd|pq|xy".toRegex()) }
        .filter { it.contains("([a-z])\\1".toRegex()) }
        .size
  }

  override fun second(data: String): Any? {
    return strings
        .filter { it.contains("([a-z]).\\1".toRegex()) }
        .filter { it.contains("([a-z]{2}).*\\1".toRegex()) }
        .size
  }
}

fun main() = SomeDay.mainify(Day5)
