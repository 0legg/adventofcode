package net.olegg.aoc.year2025.day7

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.find
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2025.DayOf2025

/**
 * See [Year 2025, Day 7](https://adventofcode.com/2025/day/7)
 */
object Day7 : DayOf2025(7) {
  override fun first(): Any? {
    val start = matrix.find('S')!!
    val queue = ArrayDeque(listOf(start))
    val seen = mutableSetOf<Vector2D>()
    val splits = mutableSetOf<Vector2D>()
    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      if (!seen.add(curr)) {
        continue
      }
      val next = curr + D.step
      when (matrix[next]) {
        '.' -> queue.add(next)
        '^' -> {
          queue.add(next + L.step)
          queue.add(next + R.step)
          splits.add(curr)
        }
        else -> Unit
      }
    }

    return splits.size
  }
}

fun main() = SomeDay.mainify(Day7)
