package net.olegg.aoc.year2017.day4

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 4](https://adventofcode.com/2017/day/4)
 */
object Day4 : DayOf2017(4) {
  override fun first(data: String): Any? {
    return data
      .trim()
      .lines()
      .map { line -> line.split(" ") }
      .count { it.size == it.toSet().size }
  }

  override fun second(data: String): Any? {
    return data
      .trim()
      .lines()
      .map { line -> line.split(" ") }
      .map { words -> words.map { String(it.toCharArray().sortedArray()) } }
      .count { it.size == it.toSet().size }
  }
}

fun main() = SomeDay.mainify(Day4)
