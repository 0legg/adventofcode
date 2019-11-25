package net.olegg.adventofcode.year2017.day4

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * See [Year 2017, Day 4](https://adventofcode.com/2017/day/4)
 */
class Day4 : DayOf2017(4) {
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

fun main() = SomeDay.mainify(Day4::class)
