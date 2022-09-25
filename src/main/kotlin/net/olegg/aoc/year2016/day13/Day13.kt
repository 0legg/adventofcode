package net.olegg.aoc.year2016.day13

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 13](https://adventofcode.com/2016/day/13)
 */
object Day13 : DayOf2016(13) {
  override fun first(): Any? {
    val fav = data.toInt()

    val target = Vector2D(31, 39)
    val start = Vector2D(1, 1) to 0

    val known = mutableMapOf(start)
    val queue = ArrayDeque(listOf(Vector2D(1, 1) to 0))

    do {
      val (pos, step) = queue.removeFirst()

      val nexts = Neighbors4
        .map { pos + it.step }
        .filter { it.x >= 0 && it.y >= 0 }
        .filter { it.isOpen(fav) }
        .filterNot { it in known }

      nexts.forEach { next ->
        known[next] = step + 1
        queue += (next to step + 1)
      }
    } while (target !in known)

    return known[target]
  }

  override fun second(): Any? {
    val fav = data.toInt()

    val start = Vector2D(1, 1) to 0

    val known = mutableMapOf(start)
    val queue = ArrayDeque(listOf(Vector2D(1, 1) to 0))

    do {
      val (pos, step) = queue.removeFirst()

      val nexts = Neighbors4
        .map { pos + it.step }
        .filter { it.x >= 0 && it.y >= 0 }
        .filter { it.isOpen(fav) }
        .filterNot { it in known }

      nexts.forEach { next ->
        known[next] = step + 1
        queue += (next to step + 1)
      }
    } while (queue.first().second <= 50)

    return known.count { it.value <= 50 }
  }

  private fun Vector2D.isOpen(fav: Int) =
    Integer.bitCount(x * x + 3 * x + 2 * x * y + y + y * y + fav) % 2 == 0
}

fun main() = SomeDay.mainify(Day13)
