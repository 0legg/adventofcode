package net.olegg.aoc.year2021.day1

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 1](https://adventofcode.com/2021/day/1)
 */
object Day1 : DayOf2021(1) {
  override fun first(data: String): Any? {
    val nums = data.parseInts(delimiters = "\n")

    return nums.windowed(2).count { it.first() < it.last() }
  }

  override fun second(data: String): Any? {
    val nums = data.parseInts(delimiters = "\n")

    return nums.windowed(3)
      .map { it.sum() }
      .windowed(2)
      .count { it.first() < it.last() }
  }
}

fun main() = SomeDay.mainify(Day1)
