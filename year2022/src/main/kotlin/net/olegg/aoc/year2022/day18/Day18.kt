package net.olegg.aoc.year2022.day18

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector3D
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 18](https://adventofcode.com/2022/day/18)
 */
object Day18 : DayOf2022(18) {
  override fun first(): Any? {
    val blocks = lines
      .map { it.parseInts(",") }
      .map { Vector3D(it[0], it[1], it[2]) }
      .toSet()

    val dirs = listOf(
      Vector3D(1, 0, 0),
      Vector3D(-1, 0, 0),
      Vector3D(0, 1, 0),
      Vector3D(0, -1, 0),
      Vector3D(0, 0, 1),
      Vector3D(0, 0, -1),
    )

    return blocks.sumOf { block ->
      dirs.count { (block + it) !in blocks }
    }
  }
}

fun main() = SomeDay.mainify(Day18)
