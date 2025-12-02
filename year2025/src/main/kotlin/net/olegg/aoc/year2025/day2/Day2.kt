package net.olegg.aoc.year2025.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2025.DayOf2025

/**
 * See [Year 2025, Day 2](https://adventofcode.com/2025/day/2)
 */
object Day2 : DayOf2025(2) {
  override fun first(): Any? {
    val ranges = data
      .split(",")
      .map { it.parseLongs("-").toPair() }

    return ranges.sumOf { (start, end) ->
      (start..end).filter { curr ->
        val str = curr.toString()
        str == str.take(str.length / 2).repeat(2)
      }.sum()
    }
  }

  override fun second(): Any? {
    val ranges = data
      .split(",")
      .map { it.parseLongs("-").toPair() }

    return ranges.sumOf { (start, end) ->
      (start..end).filter { curr ->
        val str = curr.toString()
        (1..str.length / 2).any { len ->
          str.length % len == 0 && str.take(len).repeat(str.length / len) == str
        }
      }.sum()
    }
  }
}

fun main() = SomeDay.mainify(Day2)
