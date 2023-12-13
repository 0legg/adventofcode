package net.olegg.aoc.year2023.day13

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 13](https://adventofcode.com/2023/day/13)
 */
object Day13 : DayOf2023(13) {
  override fun first(): Any? {
    val patterns = data.split("\n\n").map { it.lines() }
    val transposed = patterns.map { it.transpose() }

    return transposed.sumOf { it.rowsBeforeReflection() } + 100 * patterns.sumOf { it.rowsBeforeReflection() }
  }

  private fun List<String>.transpose(): List<String> {
    return List(first().length) { index ->
      String(map { it[index] }.toCharArray())
    }
  }

  private fun List<String>.rowsBeforeReflection(): Int {
    val rev = asReversed()
    for (i in 1..<size) {
      val below = drop(i)
      val above = rev.takeLast(i)

      if (below.zip(above).all { (a, b) -> a == b }) {
        return i
      }
    }
    return 0
  }
}

fun main() = SomeDay.mainify(Day13)
