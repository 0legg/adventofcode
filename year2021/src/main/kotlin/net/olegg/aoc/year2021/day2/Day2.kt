package net.olegg.aoc.year2021.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.Vector3D
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 2](https://adventofcode.com/2021/day/2)
 */
object Day2 : DayOf2021(2) {
  override fun first(): Any? {
    return lines
      .fold(Vector2D()) { acc, value ->
        val (dir, amountStr) = value.split(" ")
        val amount = amountStr.toInt()
        when (dir) {
          "forward" -> acc + Vector2D(x = amount)
          "up" -> acc + Vector2D(y = -amount)
          "down" -> acc + Vector2D(y = +amount)
          else -> acc
        }
      }
      .let { it.x * it.y }
  }

  override fun second(): Any? {
    return lines
      .fold(Vector3D()) { acc, value ->
        val (dir, amountStr) = value.split(" ")
        val amount = amountStr.toInt()
        when (dir) {
          "forward" -> acc + Vector3D(x = amount, y = amount * acc.z)
          "up" -> acc + Vector3D(z = -amount)
          "down" -> acc + Vector3D(z = +amount)
          else -> acc
        }
      }
      .let { it.x * it.y }
  }
}

fun main() = SomeDay.mainify(Day2)
