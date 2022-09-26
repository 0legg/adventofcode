package net.olegg.aoc.year2019.day1

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2019.DayOf2019

/**
 * See [Year 2019, Day 1](https://adventofcode.com/2019/day/1)
 */
object Day1 : DayOf2019(1) {
  override fun first(): Any? {
    return lines
      .map { it.toInt() }
      .sumOf { (it / 3) - 2 }
  }

  override fun second(): Any? {
    return lines
      .map { it.toInt() }
      .sumOf { mass ->
        generateSequence(mass) { (it / 3) - 2 }
          .drop(1)
          .takeWhile { it > 0 }
          .sum()
      }
  }
}

fun main() = SomeDay.mainify(Day1)
