package net.olegg.aoc.year2017.day13

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 13](https://adventofcode.com/2017/day/13)
 */
object Day13 : DayOf2017(13) {
  override fun first(): Any? {
    return lines
      .map { it.parseInts(": ") }
      .filter { it.first() % ((it.last() - 1) * 2) == 0 }
      .sumOf { it.first() * it.last() }
  }

  override fun second(): Any? {
    val filters = lines
      .map { it.parseInts(": ") }

    return generateSequence(0) { it + 1 }
      .first { delay ->
        filters.none { (it.first() + delay) % ((it.last() - 1) * 2) == 0 }
      }
  }
}

fun main() = SomeDay.mainify(Day13)
