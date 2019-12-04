package net.olegg.aoc.year2019.day4

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.series
import net.olegg.aoc.year2019.DayOf2019

/**
 * See [Year 2019, Day 4](https://adventofcode.com/2019/day/4)
 */
object Day4 : DayOf2019(4) {
  override fun first(data: String): Any? {
    val (from, to) = data
        .trim()
        .parseInts("-")

    return (from..to)
        .map { it.toString() }
        .filter { it.length == 6 }
        .filter { value -> value.windowed(2).any { it[0] == it[1] } }
        .filter { value -> value.windowed(2).none { it[0] > it[1] } }
        .count()
  }

  override fun second(data: String): Any? {
    val (from, to) = data
        .trim()
        .parseInts("-")

    return (from..to)
        .map { it.toString() }
        .filter { it.length == 6 }
        .filter { value -> value.windowed(2).any { it[0] == it[1] } }
        .filter { value -> value.windowed(2).none { it[0] > it[1] } }
        .filter { value -> value.toList().series().any { it.size == 2 } }
        .count()
  }
}

fun main() = SomeDay.mainify(Day4)
