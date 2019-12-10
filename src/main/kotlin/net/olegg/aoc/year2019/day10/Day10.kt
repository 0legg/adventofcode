package net.olegg.aoc.year2019.day10

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2019.DayOf2019
import kotlin.math.abs

/**
 * See [Year 2019, Day 10](https://adventofcode.com/2019/day/10)
 */
object Day10 : DayOf2019(10) {
  override fun first(data: String): Any? {
    val asteroids = data
        .trim()
        .lines()
        .mapIndexed { y, line ->
          line.mapIndexedNotNull { x, c -> if (c == '#') Vector2D(x, y) else null }
        }
        .flatten()

    val visible = asteroids.map { base ->
      asteroids.filter { it != base }
          .map { other ->
            val diff = other - base
            when {
              diff.x == 0 -> Vector2D(0, diff.y / abs(diff.y))
              diff.y == 0 -> Vector2D(diff.x / abs(diff.x), 0)
              else -> Vector2D(diff.x / gcd(diff.x, diff.y), diff.y / gcd(diff.x, diff.y))
            }
          }
          .distinct()
          .count()
    }

    return visible.max()
  }

  private fun gcd(a: Int, b: Int): Int {
    var (ta, tb) = (minOf(abs(a), abs(b)) to maxOf(abs(a), abs(b)))
    while (ta != 0) {
      val na = tb % ta
      tb = ta
      ta = na
    }
    return tb
  }
}

fun main() = SomeDay.mainify(Day10)
