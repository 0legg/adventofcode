package net.olegg.aoc.year2016.day1

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.CCW
import net.olegg.aoc.utils.CW
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 1](https://adventofcode.com/2016/day/1)
 */
object Day1 : DayOf2016(1) {
  private val SHIFT = mapOf(
      'L' to CCW,
      'R' to CW
  )

  override fun first(data: String): Any? {
    return data
        .trim()
        .split(", ")
        .map { it[0] to it.substring(1).toInt() }
        .fold(Pair(Vector2D(), U)) { (pos, dir), command ->
          val newDir = SHIFT[command.first]?.get(dir) ?: dir
          Pair(pos + newDir.step * command.second, newDir)
        }
        .first
        .manhattan()
  }

  override fun second(data: String): Any? {
    val visited = mutableSetOf<Vector2D>()

    data.trim()
        .split(", ")
        .map { it[0] to it.substring(1).toInt() }
        .fold(Pair(Vector2D(), U)) { (pos, dir), command ->
          val newDir = SHIFT[command.first]?.get(dir) ?: dir
          val steps = (1..command.second).map { pos + newDir.step * it }
          steps
              .firstOrNull { it in visited }
              ?.let { return it.manhattan() }

          visited.addAll(steps)
          return@fold Pair(pos + newDir.step * command.second, newDir)
        }

    return null
  }
}

fun main() = SomeDay.mainify(Day1)
