package net.olegg.aoc.year2023.day18

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 18](https://adventofcode.com/2023/day/18)
 */
object Day18 : DayOf2023(18) {
  override fun first(): Any? {
    val steps = lines.map { it.split(" ").toPair() }
      .map { Directions.valueOf(it.first) to it.second.toInt() }

    return solve(steps)
  }

  override fun second(): Any? {
    val dirMapping = mapOf(
      '0' to R,
      '1' to D,
      '2' to L,
      '3' to U,
    )
    val steps = lines.map { it.takeLast(7).dropLast(1) }
      .map { dirMapping.getValue(it.last()) to it.take(5).toInt(16) }

    return solve(steps)
  }

  private fun solve(steps: List<Pair<Directions, Int>>): Long {
    val points = steps.scan(Vector2D()) { acc, (dir, count) ->
      acc + dir.step * count
    }
    val perimeter = steps.sumOf { it.second.toLong() }

    val xs = points.map { it.x.toLong() }
    val ys = points.map { it.y.toLong() }

    return (xs.zip(ys.drop(1), Long::times).sum() - ys.zip(xs.drop(1), Long::times).sum()) / 2 + (perimeter / 2 + 1)
  }
 }

fun main() = SomeDay.mainify(Day18)
