package net.olegg.aoc.year2018.day11

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 11](https://adventofcode.com/2018/day/11)
 */
object Day11 : DayOf2018(11) {
  override fun first(data: String): Any? {
    val serial = data.trim().toInt()

    val grid = (1..300).map { y ->
      (1..300).map { x ->
        val rack = x + 10
        val start = rack * y
        val withSerial = start + serial
        val byId = withSerial * rack
        val digit = (byId / 100) % 10
        digit - 5
      }
    }

    return (1..298).flatMap { y ->
      (1..298).map { x ->
        val sum = (-1..1).sumOf { dy -> (-1..1).sumOf { dx -> grid[y + dy][x + dx] } }
        (x to y) to sum
      }
    }
      .maxByOrNull { it.second }
      ?.let { "${it.first.first},${it.first.second}" }
  }

  override fun second(data: String): Any? {
    val serial = data.trim().toInt()

    val grid = (1..300).map { y ->
      (1..300).map { x ->
        val rack = x + 10
        val start = rack * y
        val withSerial = start + serial
        val byId = withSerial * rack
        val digit = (byId / 100) % 10
        digit - 5
      }
    }

    return (0..299).flatMap { y ->
      (0..299).flatMap { x ->
        (0..299).mapNotNull { size ->
          return@mapNotNull if (x + size < 300 && y + size < 300) {
            val sum = (0..size).sumOf { dy -> (0..size).sumOf { dx -> grid[y + dy][x + dx] } }
            Triple(x + 1, y + 1, size + 1) to sum
          } else {
            null
          }
        }
      }
    }
      .maxByOrNull { it.second }
      ?.let { "${it.first.first},${it.first.second},${it.first.third}" }
  }
}

fun main() = SomeDay.mainify(Day11)
