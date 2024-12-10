package net.olegg.aoc.year2024.day10

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.set
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 10](https://adventofcode.com/2024/day/10)
 */
object Day10 : DayOf2024(10) {
  private val heights = lines.map { row ->
    row.map { it.digitToInt() }
  }

  override fun first(): Any? {
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

  override fun second(): Any? {
    val heads = heights.flatMapIndexed { y, row ->
      row.mapIndexedNotNull { x, c ->
        if (c == 0) Vector2D(x, y) else null
      }
    }

    return heads.sumOf { head ->
      val ends = mutableSetOf<Vector2D>()

      val options = heights.map { it.map { 0 }.toMutableList() }
      options[head] = 1

      val queue = ArrayDeque(listOf(head))
      val visited = mutableSetOf<Vector2D>()

      while (queue.isNotEmpty()) {
        val curr = queue.removeFirst()
        if (curr in visited) {
          continue
        }
        visited += curr
        val height = heights[curr]!!

        if (heights[curr] == 9) {
          ends += curr
        } else {
          val next = Directions.NEXT_4
            .map { curr + it.step }
            .filter { heights[it] == height + 1 }

          next.forEach {
            options[it] = options[it]!! + options[curr]!!
          }
          queue += next
        }
      }

      ends.sumOf { options[it]!! }
    }
  }
}

fun main() = SomeDay.mainify(Day10)
