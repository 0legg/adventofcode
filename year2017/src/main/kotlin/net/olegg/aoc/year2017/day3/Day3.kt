package net.olegg.aoc.year2017.day3

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.NEXT_8
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2017.DayOf2017
import kotlin.math.abs

/**
 * See [Year 2017, Day 3](https://adventofcode.com/2017/day/3)
 */
object Day3 : DayOf2017(3) {
  override fun first(): Any? {
    val position = data.toInt()

    val square = (1..Int.MAX_VALUE step 2).first { it * it >= position }
    val relative = position - (square - 2) * (square - 2)
    val diff = (relative - 1) % (square - 1)

    return (square / 2) + abs(diff - (square / 2 - 1))
  }

  override fun second(): Any? {
    val input = data.toInt()

    var current = Vector2D(0, 0) to 1
    val visited = mutableMapOf(current)
    var square = 0
    while (current.second < input) {
      val (pos, _) = current
      val nextPos = pos + when {
        pos.y == square -> R
        pos.x == square && pos.y > -square -> U
        pos.y == -square && pos.x > -square -> L
        pos.x == -square && pos.y < square -> D
        else -> R
      }.step

      if (abs(nextPos.x) > square || abs(nextPos.y) > square) {
        square += 1
      }

      val value = NEXT_8.sumOf { visited[nextPos + it.step] ?: 0 }
      current = nextPos to value
      visited += current
    }

    return current.second
  }
}

fun main() = SomeDay.mainify(Day3)
