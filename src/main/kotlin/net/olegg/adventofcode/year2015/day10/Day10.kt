package net.olegg.adventofcode.year2015.day10

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.utils.series
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * See [Year 2015, Day 10](https://adventofcode.com/2015/day/10)
 */
class Day10 : DayOf2015(10) {
  fun lookAndSay(source: String) = source
      .toList()
      .series()
      .joinToString(separator = "") { "${it.size}${it.first()}" }

  override fun first(data: String): Any? {
    return (1..40).fold(data) { acc, _ -> lookAndSay(acc) }.length
  }

  override fun second(data: String): Any? {
    return (1..50).fold(data) { acc, _ -> lookAndSay(acc) }.length
  }
}

fun main() = SomeDay.mainify(Day10::class)
