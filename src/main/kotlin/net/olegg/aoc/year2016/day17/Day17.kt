package net.olegg.aoc.year2016.day17

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.md5
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 17](https://adventofcode.com/2016/day/17)
 */
object Day17 : DayOf2016(17) {
  override fun first(data: String): Any? {
    val start = Triple(1, 1, "")
    val moves = listOf(
      Triple(0, -1, 'U'),
      Triple(0, 1, 'D'),
      Triple(-1, 0, 'L'),
      Triple(1, 0, 'R')
    )

    val queue = mutableListOf(start)

    do {
      val (x, y, tail) = queue.removeAt(0)

      val next = moves
        .zip("$data$tail".md5().substring(0, 4).uppercase().toList())
        .filter { it.second in "BCDEF" }
        .map { Triple(x + it.first.first, y + it.first.second, tail + it.first.third) }
        .filter { it.first in 1..4 && it.second in 1..4 }

      queue.addAll(next)

      val found = next.any { it.first == 4 && it.second == 4 }
    } while (!found)

    return queue.first { it.first == 4 && it.second == 4 }.third
  }

  override fun second(data: String): Any? {
    val start = Triple(1, 1, "")
    val moves = listOf(
      Triple(0, -1, 'U'),
      Triple(0, 1, 'D'),
      Triple(-1, 0, 'L'),
      Triple(1, 0, 'R')
    )

    var best = 0
    val queue = mutableListOf(start)

    do {
      val (x, y, tail) = queue.removeAt(0)

      if (x == 4 && y == 4) {
        best = maxOf(best, tail.length)
        continue
      }

      val next = moves
        .zip("$data$tail".md5().substring(0, 4).uppercase().toList())
        .filter { it.second in "BCDEF" }
        .map { Triple(x + it.first.first, y + it.first.second, tail + it.first.third) }
        .filter { it.first in 1..4 && it.second in 1..4 }

      queue.addAll(next)
    } while (queue.isNotEmpty())

    return best.toString()
  }
}

fun main() = SomeDay.mainify(Day17)
