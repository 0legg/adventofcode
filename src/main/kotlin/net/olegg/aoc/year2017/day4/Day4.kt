package net.olegg.aoc.year2017.day4

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 4](https://adventofcode.com/2017/day/4)
 */
object Day4 : DayOf2017(4) {
  override fun first(data: String): Any? {
    return data.lines()
        .map { it.split("\\s".toRegex()) }
        .filter { it.size == it.toSet().size }
        .count()
  }

  override fun second(data: String): Any? {
    return data.lines()
        .map { it.split("\\s".toRegex()) }
        .map { it.map { String(it.toCharArray().sortedArray()) } }
        .filter { it.size == it.toSet().size }
        .count()
  }
}

fun main() = SomeDay.mainify(Day4)
