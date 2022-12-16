package net.olegg.aoc.year2021.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 6](https://adventofcode.com/2021/day/6)
 */
object Day6 : DayOf2021(6) {
  override fun first(): Any? {
    return solve(80)
  }

  override fun second(): Any? {
    return solve(256)
  }

  private fun solve(days: Int): Long {
    val input = data.parseInts(",")

    val counts = input
      .groupingBy { it }
      .eachCount()

    val start = List(10) { counts.getOrDefault(it, 0).toLong() }

    val end = (1..days).fold(start) { acc, _ ->
      val reset = acc.first()
      acc.drop(1).mapIndexed { index, value ->
        when (index) {
          6, 8 -> value + reset
          else -> value
        }
      } + listOf(0L)
    }

    return end.sum()
  }
}

fun main() = SomeDay.mainify(Day6)
