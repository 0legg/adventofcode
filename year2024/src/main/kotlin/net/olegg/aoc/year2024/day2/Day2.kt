package net.olegg.aoc.year2024.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2024.DayOf2024
import kotlin.math.absoluteValue

/**
 * See [Year 2024, Day 2](https://adventofcode.com/2024/day/2)
 */
object Day2 : DayOf2024(2) {
  override fun first(): Any? {
    return lines
      .map { it.parseInts(" ") }
      .count { it.isSafe() }
  }

  override fun second(): Any? {
    return lines
      .map { it.parseInts(" ") }
      .count { report ->
        report.indices.map { report.toMutableList().apply { removeAt(it) } }.any { it.isSafe() }
      }
  }

  private fun List<Int>.isSafe(): Boolean {
    val sorted = sorted()
    return (this == sorted || asReversed() == sorted) && sorted.zipWithNext { a, b -> b - a }.all { it in 1..3 }
  }
}

fun main() = SomeDay.mainify(Day2)
