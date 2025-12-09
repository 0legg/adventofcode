package net.olegg.aoc.year2025.day9

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.pairs
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2025.DayOf2025
import kotlin.math.abs

/**
 * See [Year 2025, Day 9](https://adventofcode.com/2025/day/9)
 */
object Day9 : DayOf2025(9) {
  override fun first(): Any? {
    return lines
      .map { it.parseLongs(",").toPair() }
      .pairs()
      .maxOf { (a, b) ->
        (abs(a.first - b.first) + 1) * (abs(a.second - b.second) + 1)
      }
  }
}

fun main() = SomeDay.mainify(Day9)
