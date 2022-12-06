package net.olegg.aoc.year2019.day4

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.series
import net.olegg.aoc.year2019.DayOf2019

/**
 * See [Year 2019, Day 4](https://adventofcode.com/2019/day/4)
 */
object Day4 : DayOf2019(4) {
  override fun first(): Any? {
    val (from, to) = data.parseInts("-")

    return (from..to)
      .asSequence()
      .map { it.toString() }
      .filter { it.length == 6 }
      .filter { value -> value.zipWithNext().any { it.first == it.second } }
      .count { value -> value.zipWithNext().none { it.first > it.second } }
  }

  override fun second(): Any? {
    val (from, to) = data.parseInts("-")

    return (from..to)
      .asSequence()
      .map { it.toString() }
      .filter { it.length == 6 }
      .filter { value -> value.zipWithNext().any { it.first == it.second } }
      .filter { value -> value.zipWithNext().none { it.first > it.second } }
      .count { value -> value.toList().series().any { it.second == 2 } }
  }
}

fun main() = SomeDay.mainify(Day4)
