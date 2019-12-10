package net.olegg.aoc.year2019.day10

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2019.DayOf2019
import kotlin.math.abs
import kotlin.math.atan2

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
          .map { base.direction(it) }
          .distinct()
          .count()
    }

    return visible.max()
  }

  override fun second(data: String): Any? {
    val asteroids = data
        .trim()
        .lines()
        .mapIndexed { y, line ->
          line.mapIndexedNotNull { x, c -> if (c == '#') Vector2D(x, y) else null }
        }
        .flatten()

    val base = asteroids.maxBy { curr ->
      asteroids.filter { it != curr }
          .map { curr.direction(it) }
          .distinct()
          .count()
    } ?: Vector2D(0, 0)

    val lines = (asteroids - base)
        .groupBy { other -> base.direction(other) }
        .mapValues { (_, line) -> line.sortedBy { (it - base).length2() } }
        .toList()
        .map { (dir, list) -> atan2(-dir.y.toDouble(), dir.x.toDouble()) to list }
        .sortedByDescending { it.first }

    val linesFromTop = (lines.dropWhile { it.first > atan2(1.0, 0.0) } + lines.takeWhile { it.first > atan2(1.0, 0.0) })
        .map { it.second }

    val maxLength = linesFromTop.map { it.size }.max() ?: 0

    val ordered = (0 until maxLength).flatMap { pos ->
      linesFromTop.mapNotNull { line -> line.getOrNull(pos) }
    }

    return ordered[199].let { it.x * 100 + it.y }
  }

  private fun Vector2D.direction(other: Vector2D): Vector2D {
    val diff = other - this
    return when {
      diff.x == 0 -> Vector2D(0, diff.y / abs(diff.y))
      diff.y == 0 -> Vector2D(diff.x / abs(diff.x), 0)
      else -> Vector2D(diff.x / gcd(diff.x, diff.y), diff.y / gcd(diff.x, diff.y))
    }
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
