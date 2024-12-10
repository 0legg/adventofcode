package net.olegg.aoc.year2024.day10

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 10](https://adventofcode.com/2024/day/10)
 */
object Day10 : DayOf2024(10) {
  override fun first(): Any? {
    val heights = lines.map { row ->
      row.map { it.digitToInt() }
    }

    val heads = heights.flatMapIndexed { y, row ->
      row.mapIndexedNotNull { x, c ->
        if (c == 0) Vector2D(x, y) else null
      }
    }

    return heads.sumOf { head ->
      val ends = mutableSetOf<Vector2D>()

      val queue = ArrayDeque(listOf(head))

      while (queue.isNotEmpty()) {
        val curr = queue.removeFirst()
        val height = heights[curr]!!

        if (heights[curr] == 9) {
          ends += curr
        } else {
          queue += Directions.NEXT_4
            .map { curr + it.step }
            .filter { heights[it] == height + 1 }
        }
      }

      ends.size
    }
  }
}

fun main() = SomeDay.mainify(Day10)
