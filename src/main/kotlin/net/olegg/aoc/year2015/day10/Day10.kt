package net.olegg.aoc.year2015.day10

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.series
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 10](https://adventofcode.com/2015/day/10)
 */
object Day10 : DayOf2015(10) {
  private fun lookAndSay(source: String) = source
    .toList()
    .series()
    .joinToString(separator = "") { "${it.second}${it.first}" }

  override fun first(): Any? {
    return (1..40).fold(data) { acc, _ -> lookAndSay(acc) }.length
  }

  override fun second(): Any? {
    return (1..50).fold(data) { acc, _ -> lookAndSay(acc) }.length
  }
}

fun main() = SomeDay.mainify(Day10)
