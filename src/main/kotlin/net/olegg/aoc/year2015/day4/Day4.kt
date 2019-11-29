package net.olegg.aoc.year2015.day4

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.md5
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 4](https://adventofcode.com/2015/day/4)
 */
object Day4 : DayOf2015(4) {
  override fun first(data: String): Any? {
    return generateSequence(1) { it + 1 }.first { "${data.trim()}$it".md5().startsWith("00000") }
  }

  override fun second(data: String): Any? {
    return generateSequence(1) { it + 1 }.first { "${data.trim()}$it".md5().startsWith("000000") }
  }
}

fun main() = SomeDay.mainify(Day4)
