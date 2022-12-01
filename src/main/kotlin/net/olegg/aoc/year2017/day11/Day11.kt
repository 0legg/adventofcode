package net.olegg.aoc.year2017.day11

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2017.DayOf2017
import kotlin.math.abs

/**
 * See [Year 2017, Day 11](https://adventofcode.com/2017/day/11)
 */
object Day11 : DayOf2017(11) {
  override fun first(): Any? {
    return data
      .split(",")
      .fold(Vector2D()) { acc, value ->
        acc + when (value) {
          "nw" -> Vector2D(-1, 0)
          "n" -> Vector2D(0, 1)
          "ne" -> Vector2D(1, 1)
          "sw" -> Vector2D(-1, -1)
          "s" -> Vector2D(0, -1)
          "se" -> Vector2D(1, 0)
          else -> Vector2D()
        }
      }
      .let {
        maxOf(abs(it.x), abs(it.y), abs(it.x - it.y))
      }
  }

  override fun second(): Any? {
    return data
      .split(",")
      .fold(Vector2D() to 0) { (pos, speed), value ->
        val next = pos + when (value) {
          "nw" -> Vector2D(-1, 0)
          "n" -> Vector2D(0, 1)
          "ne" -> Vector2D(1, 1)
          "sw" -> Vector2D(-1, -1)
          "s" -> Vector2D(0, -1)
          "se" -> Vector2D(1, 0)
          else -> Vector2D()
        }

        val dist = maxOf(speed, abs(next.x), abs(next.y), abs(next.x - next.y))

        return@fold next to dist
      }
      .second
  }
}

fun main() = SomeDay.mainify(Day11)
