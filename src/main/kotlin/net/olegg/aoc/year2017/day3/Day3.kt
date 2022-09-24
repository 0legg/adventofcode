package net.olegg.aoc.year2017.day3

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017
import kotlin.math.abs

/**
 * See [Year 2017, Day 3](https://adventofcode.com/2017/day/3)
 */
object Day3 : DayOf2017(3) {
  override fun first(): Any? {
    val position = data.trim().toInt()

    val square = (1..Int.MAX_VALUE step 2).first { it * it >= position }
    val relative = position - (square - 2) * (square - 2)
    val diff = (relative - 1) % (square - 1)

    return (square / 2) + abs(diff - (square / 2 - 1))
  }

  override fun second(): Any? {
    val visited = mutableMapOf((0 to 0) to 1)
    val input = data.trim().toInt()

    var current = (0 to 0) to 1
    var square = 0
    while (current.second < input) {
      val position = when {
        current.first.second == -square -> {
          current.first.first + 1 to current.first.second
        }
        current.first.first == square && current.first.second < square -> {
          current.first.first to current.first.second + 1
        }
        current.first.second == square && current.first.first > -square -> {
          current.first.first - 1 to current.first.second
        }
        current.first.first == -square && current.first.second > -square -> {
          current.first.first to current.first.second - 1
        }
        else -> {
          current.first.first + 1 to current.first.second
        }
      }

      if (abs(position.first) > square || abs(position.second) > square) {
        square += 1
      }

      val value = (-1..1).flatMap { x -> (-1..1).map { y -> x to y } }
        .mapNotNull { visited[position.first + it.first to position.second + it.second] }
        .sum()
      current = position to value
      visited += current
    }

    return current.second
  }
}

fun main() = SomeDay.mainify(Day3)
