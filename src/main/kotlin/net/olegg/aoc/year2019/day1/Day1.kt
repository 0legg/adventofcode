package net.olegg.aoc.year2019.day1

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2019.DayOf2019

/**
 * See [Year 2019, Day 1](https://adventofcode.com/2019/day/1)
 */
object Day1 : DayOf2019(1) {
  override fun first(data: String): Any? {
    return data
      .trim()
      .lines()
      .map { it.toInt() }
      .map { (it / 3) - 2 }
      .sum()
  }

  override fun second(data: String): Any? {
    return data
      .trim()
      .lines()
      .map { it.toInt() }
      .map { mass ->
        generateSequence(mass) { (it / 3) - 2 }
          .drop(1)
          .takeWhile { it > 0 }
          .sum()
      }
      .sum()
  }
}

fun main() = SomeDay.mainify(Day1)
