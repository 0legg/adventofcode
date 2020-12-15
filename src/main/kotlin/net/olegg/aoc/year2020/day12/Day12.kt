package net.olegg.aoc.year2020.day12

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.CCW
import net.olegg.aoc.utils.CW
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 12](https://adventofcode.com/2020/day/12)
 */
object Day12 : DayOf2020(12) {
  override fun first(data: String): Any? {
    val route = data
      .trim()
      .lines()
      .map { it.first() to it.drop(1).toInt() }

    val target = route.fold(Vector2D() to R) { acc, (op, size) ->
      when (op) {
        'N' -> acc.first + U.step * size to acc.second
        'W' -> acc.first + L.step * size to acc.second
        'E' -> acc.first + R.step * size to acc.second
        'S' -> acc.first + D.step * size to acc.second
        'L' -> acc.first to (0 until size / 90).fold(acc.second) { dir, _ -> CCW[dir] ?: dir }
        'R' -> acc.first to (0 until size / 90).fold(acc.second) { dir, _ -> CW[dir] ?: dir }
        'F' -> acc.first + acc.second.step * size to acc.second
        else -> acc
      }
    }

    return target.first.manhattan()
  }

  override fun second(data: String): Any? {
    val route = data
      .trim()
      .lines()
      .map { it.first() to it.drop(1).toInt() }

    val target = route.fold(Vector2D() to Vector2D(10, -1)) { acc, (op, size) ->
      when (op) {
        'N' -> acc.first to acc.second + U.step * size
        'W' -> acc.first to acc.second + L.step * size
        'E' -> acc.first to acc.second + R.step * size
        'S' -> acc.first to acc.second + D.step * size
        'L' -> acc.first to (0 until size / 90).fold(acc.second) { dir, _ -> Vector2D(dir.y, -dir.x) }
        'R' -> acc.first to (0 until size / 90).fold(acc.second) { dir, _ -> Vector2D(-dir.y, dir.x) }
        'F' -> acc.first + acc.second * size to acc.second
        else -> acc
      }
    }

    return target.first.manhattan()
  }
}

fun main() = SomeDay.mainify(Day12)
