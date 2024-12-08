package net.olegg.aoc.year2024.day8

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.fit
import net.olegg.aoc.utils.pairs
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 8](https://adventofcode.com/2024/day/8)
 */
object Day8 : DayOf2024(8) {
  override fun first(): Any? {
    val antinodes = mutableSetOf<Vector2D>()

    val points = matrix
      .flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
          if (c != '.') c to Vector2D(x, y) else null
        }
      }
      .groupingBy { it.first }
      .fold(emptyList<Vector2D>()) { acc, (_, value) -> acc + value }

    points.forEach { (_, set) ->
      set.pairs().forEach { (a, b) ->
        val delta = b - a
        antinodes += a - delta
        antinodes += b + delta
      }
    }

    return antinodes.count { matrix.fit(it) }
  }
}

fun main() = SomeDay.mainify(Day8)
