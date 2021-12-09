package net.olegg.aoc.year2021.day9

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 9](https://adventofcode.com/2021/day/9)
 */
object Day9 : DayOf2021(9) {
  override fun first(data: String): Any? {
    val points = data.trim()
      .lines()
      .map { line -> line.toList().map { it.digitToInt() } }

    return points.mapIndexed { y, line ->
      line.filterIndexed { x, value ->
        val point = Vector2D(x, y)
        Neighbors4.map { point + it.step }
          .all { neighbor ->
            points[neighbor]?.let { it > value } ?: true
          }
      }.sumOf { it + 1 }
    }.sum()
  }
}

fun main() = SomeDay.mainify(Day9)
