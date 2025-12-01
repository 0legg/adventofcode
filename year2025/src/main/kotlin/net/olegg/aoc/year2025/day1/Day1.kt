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
      val rawPos = when (op[0]) {
        'L' -> pos - dist
        'R' -> pos + dist
        else -> pos
      }
      (rawPos % 100 + 100) % 100
    }.count { it == 0 }
  }

  override fun second(): Any? {
    return lines.scan(50 to 0) { (pos, _), op ->
      val dist = op.drop(1).toInt()
      val rawPos = when (op[0]) {
        'L' -> pos - dist
        'R' -> pos + dist
        else -> pos
      }
      val actualPos = (rawPos % 100 + 100) % 100

      val switch = when (op[0]) {
        'L' -> (-rawPos + 100) / 100 - if (pos == 0) 1 else 0
        'R' -> rawPos / 100
        else -> 0
      }
      actualPos to switch
    }.sumOf { it.second }
  }
}

fun main() = SomeDay.mainify(Day1)
