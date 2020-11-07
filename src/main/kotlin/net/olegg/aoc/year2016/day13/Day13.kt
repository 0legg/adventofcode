package net.olegg.aoc.year2016.day13

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.DayOf2016
import java.util.ArrayDeque

/**
 * See [Year 2016, Day 13](https://adventofcode.com/2016/day/13)
 */
object Day13 : DayOf2016(13) {
  private val MOVES = listOf(
      0 to 1,
      0 to -1,
      1 to 0,
      -1 to 0
  )

  override fun first(data: String): Any? {
    val fav = data.trim().toInt()

    val target = 31 to 39

    val known = mutableMapOf((1 to 1) to 0)
    val queue = ArrayDeque(listOf(Triple(1, 1, 0)))

    do {
      val (x, y, step) = queue.poll()

      val next = MOVES
          .map { x + it.first to y + it.second }
          .filter { it.first >= 0 && it.second >= 0 }
          .filter { isOpen(it.first, it.second, fav) }
          .filter { !known.contains(it) }

      next.forEach { pos ->
        known[pos] = step + 1
        queue.offer(Triple(pos.first, pos.second, step + 1))
      }
    } while (!known.contains(target))

    return known[target]
  }

  override fun second(data: String): Any? {
    val fav = data.trim().toInt()

    val known = mutableMapOf((1 to 1) to 0)
    val queue = ArrayDeque(listOf(Triple(1, 1, 0)))

    do {
      val (x, y, step) = queue.poll()

      val next = MOVES
          .map { x + it.first to y + it.second }
          .filter { it.first >= 0 && it.second >= 0 }
          .filter { isOpen(it.first, it.second, fav) }
          .filter { !known.contains(it) }

      next.forEach { pos ->
        known[pos] = step + 1
        queue.offer(Triple(pos.first, pos.second, step + 1))
      }
    } while (queue.first().third <= 50)

    return known.filterValues { it <= 50 }.size
  }

  fun isOpen(x: Int, y: Int, fav: Int) =
      Integer.bitCount(x * x + 3 * x + 2 * x * y + y + y * y + fav) % 2 == 0
}

fun main() = SomeDay.mainify(Day13)
