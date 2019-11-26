package net.olegg.adventofcode.year2015.day5

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * See [Year 2015, Day 5](https://adventofcode.com/2015/day/5)
 */
class Day5 : DayOf2015(5) {
  val strings = data.lines()
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

fun main() = SomeDay.mainify(Day5::class)
