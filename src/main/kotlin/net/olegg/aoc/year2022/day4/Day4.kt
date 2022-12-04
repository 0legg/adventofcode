package net.olegg.aoc.year2022.day4

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 4](https://adventofcode.com/2022/day/4)
 */
object Day4 : DayOf2022(4) {
  override fun first(): Any? {
    return lines
      .map { it.parseInts("-", ",") }
      .count { line ->
        val first = IntRange(line[0], line[1])
        val second = IntRange(line[2], line[3])
        (second.first in first && second.last in first) || (first.first in second && first.last in second)
      }
  }
}

fun main() = SomeDay.mainify(Day4)
