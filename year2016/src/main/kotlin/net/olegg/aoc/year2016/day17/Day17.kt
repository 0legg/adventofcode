package net.olegg.aoc.year2016.day17

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.NEXT_4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.md5
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 17](https://adventofcode.com/2016/day/17)
 */
object Day17 : DayOf2016(17) {
  private val TARGET = Vector2D(4, 4)
  override fun first(): Any? {
    val start = Vector2D(1, 1) to ""

    val queue = mutableListOf(start)

    do {
      val (curr, tail) = queue.removeAt(0)

      val next = NEXT_4
        .zip("$data$tail".md5().substring(0, 4).uppercase().toList())
        .filter { it.second in "BCDEF" }
        .map { curr + it.first.step to tail + it.first.name }
        .filter { it.first.x in 1..4 && it.first.y in 1..4 }

      queue.addAll(next)

      val found = next.any { it.first == TARGET }
    } while (!found)

    return queue.first { it.first == TARGET }.second
  }

  override fun second(): Any? {
    val start = Vector2D(1, 1) to ""

    var best = 0
    val queue = mutableListOf(start)

    do {
      val (curr, tail) = queue.removeAt(0)

      if (curr == TARGET) {
        best = maxOf(best, tail.length)
        continue
      }

      val next = NEXT_4
        .zip("$data$tail".md5().substring(0, 4).uppercase().toList())
        .filter { it.second in "BCDEF" }
        .map { curr + it.first.step to tail + it.first.name }
        .filter { it.first.x in 1..4 && it.first.y in 1..4 }

      queue.addAll(next)
    } while (queue.isNotEmpty())

    return best
  }
}

fun main() = SomeDay.mainify(Day17)
