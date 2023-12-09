package net.olegg.aoc.year2023.day9

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 9](https://adventofcode.com/2023/day/9)
 */
object Day9 : DayOf2023(9) {
  override fun first(): Any? {
    return lines.sumOf { line ->
      val base = line.parseLongs(" ")

      generateSequence(0) { it + 1 }
        .scan(base) { acc, _ ->
          acc.zipWithNext { a, b -> b - a }
        }
        .takeWhile { row -> row.any { it != 0L } }
        .sumOf { it.last() }
    }
  }
}

fun main() = SomeDay.mainify(Day9)
