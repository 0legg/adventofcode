package net.olegg.aoc.year2019.day5

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

/**
 * See [Year 2019, Day 5](https://adventofcode.com/2019/day/5)
 */
object Day5 : DayOf2019(5) {
  override fun first(data: String): Any? {
    val program = data
        .trim()
        .parseInts(",")
        .toIntArray()

    val result = Intcode.eval(program, listOf(1))
    if (result.dropLast(1).any { it != 0 }) {
      throw IllegalStateException("Program calculation failed")
    }

    return result.last()
  }
}

fun main() = SomeDay.mainify(Day5)
