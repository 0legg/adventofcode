package net.olegg.aoc.year2015.day5

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 5](https://adventofcode.com/2015/day/5)
 */
object Day5 : DayOf2015(5) {
  private val BAD_2_CHARS_REGEX = "ab|cd|pq|xy".toRegex()
  private val AA_REGEX = "([a-z])\\1".toRegex()
  private val ABA_REGEX = "([a-z]).\\1".toRegex()
  private val ABAB_REGEX = "([a-z]{2}).*\\1".toRegex()

  override fun first(data: String): Any? {
    return data
        .trim()
        .lines()
        .filter { line -> line.count { it in "aeiou" } >= 3 }
        .filterNot { BAD_2_CHARS_REGEX in it }
        .filter { AA_REGEX in it }
        .size
  }

  override fun second(data: String): Any? {
    return data
        .trim()
        .lines()
        .filter { ABA_REGEX in it }
        .filter { ABAB_REGEX in it }
        .size
  }
}

fun main() = SomeDay.mainify(Day5)
