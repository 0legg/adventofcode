package net.olegg.aoc.year2021.day13

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 13](https://adventofcode.com/2021/day/13)
 */
object Day13 : DayOf2021(13) {
  override fun first(): Any? {
    val (rawDots, rawFolds) = data.trim().split("\n\n")
    val points = rawDots.lines()
      .map { it.parseInts(",") }
      .map { Vector2D(it.first(), it.last()) }
      .toSet()

    val folds = rawFolds.lines()
      .map { it.split("=") }
      .map { it.first().last() to it.last().toInt() }

    val first = folds.first()

    return points.map {
      when {
        first.first == 'x' && it.x > first.second -> Vector2D(2 * first.second - it.x, it.y)
        first.first == 'y' && it.y > first.second -> Vector2D(it.x, 2 * first.second - it.y)
        else -> it
      }
    }.toSet().size
  }

  override fun second(): Any? {
    val (rawDots, rawFolds) = data.trim().split("\n\n")
    val points = rawDots.lines()
      .map { it.parseInts(",") }
      .map { Vector2D(it.first(), it.last()) }
      .toSet()

    val folds = rawFolds.lines()
      .map { it.split("=") }
      .map { it.first().last() to it.last().toInt() }

    val final = folds.fold(points) { acc, fold ->
      acc.map {
        when {
          fold.first == 'x' && it.x > fold.second -> Vector2D(2 * fold.second - it.x, it.y)
          fold.first == 'y' && it.y > fold.second -> Vector2D(it.x, 2 * fold.second - it.y)
          else -> it
        }
      }.toSet()
    }

    val minX = final.minOf { it.x }
    val maxX = final.maxOf { it.x }
    val minY = final.minOf { it.y }
    val maxY = final.maxOf { it.y }
    return (minY..maxY).joinToString(prefix = "\n", separator = "\n") { y ->
      (minX..maxX).joinToString(separator = "") { x ->
        if (Vector2D(x, y) in final) "##" else ".."
      }
    }
  }
}

fun main() = SomeDay.mainify(Day13)
