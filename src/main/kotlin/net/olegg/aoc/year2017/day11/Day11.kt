package net.olegg.aoc.year2017.day11

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017
import kotlin.math.abs

/**
 * See [Year 2017, Day 11](https://adventofcode.com/2017/day/11)
 */
object Day11 : DayOf2017(11) {
  override fun first(): Any? {
    return data
      .split(",")
      .fold(0 to 0) { acc, value ->
        when (value) {
          "nw" -> acc.first - 1 to acc.second
          "n" -> acc.first to acc.second + 1
          "ne" -> acc.first + 1 to acc.second + 1
          "sw" -> acc.first - 1 to acc.second - 1
          "s" -> acc.first to acc.second - 1
          "se" -> acc.first + 1 to acc.second
          else -> acc
        }
      }
      .let {
        listOf(abs(it.first), abs(it.second), abs(it.first - it.second)).maxOrNull()
      }
  }

  override fun second(): Any? {
    return data
      .split(",")
      .fold(Triple(0, 0, 0)) { acc, value ->
        val next = when (value) {
          "nw" -> acc.first - 1 to acc.second
          "n" -> acc.first to acc.second + 1
          "ne" -> acc.first + 1 to acc.second + 1
          "sw" -> acc.first - 1 to acc.second - 1
          "s" -> acc.first to acc.second - 1
          "se" -> acc.first + 1 to acc.second
          else -> acc.first to acc.second
        }

        val dist = listOf(acc.third, abs(next.first), abs(next.second), abs(next.first - next.second)).maxOrNull()

        return@fold Triple(next.first, next.second, dist ?: 0)
      }
      .third
  }
}

fun main() = SomeDay.mainify(Day11)
