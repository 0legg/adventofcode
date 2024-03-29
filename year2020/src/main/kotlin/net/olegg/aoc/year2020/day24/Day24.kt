package net.olegg.aoc.year2020.day24

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector3D
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 24](https://adventofcode.com/2020/day/24)
 */
object Day24 : DayOf2020(24) {
  private val PATTERN = "(nw|ne|w|e|sw|se)".toRegex()
  private val DIRS = mapOf(
    "nw" to Vector3D(0, 1, -1),
    "ne" to Vector3D(1, 0, -1),
    "w" to Vector3D(-1, 1, 0),
    "e" to Vector3D(1, -1, 0),
    "sw" to Vector3D(-1, 0, 1),
    "se" to Vector3D(0, -1, 1),
  )

  override fun first(): Any? {
    val items = lines
      .map { line ->
        PATTERN.findAll(line).map { it.groupValues[1] }.toList()
      }

    val tiles = items.map { steps ->
      steps.fold(Vector3D()) { acc, value -> acc + DIRS.getValue(value) }
    }

    return tiles.groupBy { it }.count { it.value.size % 2 == 1 }
  }

  override fun second(): Any? {
    val items = lines
      .map { line ->
        PATTERN.findAll(line).map { it.groupValues[1] }.toList()
      }

    val start = items
      .map { steps ->
        steps.fold(Vector3D()) { acc, value -> acc + DIRS.getValue(value) }
      }
      .groupBy { it }
      .filterValues { it.size % 2 == 1 }
      .keys

    val result = (0..<100).fold(start) { prev, _ ->
      val neighbors = prev
        .flatMap { cell -> DIRS.values.map { it + cell } }
        .groupingBy { it }
        .eachCount()

      return@fold (prev.filter { neighbors[it] in 1..2 } + (neighbors.filterValues { it == 2 }.keys - prev)).toSet()
    }

    return result.size
  }
}

fun main() = SomeDay.mainify(Day24)
