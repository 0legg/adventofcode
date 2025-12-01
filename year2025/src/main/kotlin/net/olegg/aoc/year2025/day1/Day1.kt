package net.olegg.aoc.year2025.day1

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2025.DayOf2025

/**
 * See [Year 2025, Day 1](https://adventofcode.com/2025/day/1)
 */
object Day1 : DayOf2025(1) {
  override fun first(): Any? {
    return lines.scan(50) { pos, op ->
      val dist = op.drop(1).toInt()
      when (op[0]) {
        'L' -> ((pos - dist) % 100 + 100) % 100
        'R' -> ((pos + dist) % 100 + 100) % 100
        else -> pos
      }
    }.count { it == 0 }
  }
}

fun main() = SomeDay.mainify(Day1)
