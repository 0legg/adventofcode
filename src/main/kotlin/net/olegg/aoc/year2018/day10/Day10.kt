package net.olegg.aoc.year2018.day10

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 10](https://adventofcode.com/2018/day/10)
 */
object Day10 : DayOf2018(10) {
  private val PATTERN = "position=<\\s*(-?\\d+),\\s*(-?\\d+)> velocity=<\\s*(-?\\d+),\\s*(-?\\d+)>".toRegex()
  private const val HEIGHT = 16

  override fun first(): Any? {
    var points = lines
      .mapNotNull { line ->
        PATTERN.find(line)?.let { match ->
          val (x, y, vx, vy) = match.destructured.toList().map { it.toInt() }
          return@let (x to y) to (vx to vy)
        }
      }

    do {
      points = points.map { it.copy(first = it.first + it.second) }
      val height = points
        .map { it.first.second }
        .let { (it.maxOrNull() ?: 0) - (it.minOrNull() ?: 0) }
    } while (height > HEIGHT)

    val coords = points.map { it.first }

    val xmin = coords.minOfOrNull { it.first } ?: 0
    val xmax = coords.maxOfOrNull { it.first } ?: 0
    val ymin = coords.minOfOrNull { it.second } ?: 0
    val ymax = coords.maxOfOrNull { it.second } ?: 0

    return buildString {
      (ymin..ymax).forEach { y ->
        (xmin..xmax).forEach { x ->
          append(if (x to y in coords) "#" else ".")
        }
        append("\n")
      }
    }
  }

  override fun second(): Any? {
    var points = lines
      .mapNotNull { line ->
        PATTERN.find(line)?.let { match ->
          val (x, y, vx, vy) = match.destructured.toList().map { it.toInt() }
          return@let (x to y) to (vx to vy)
        }
      }
    var seconds = 0
    do {
      seconds++
      points = points.map { it.copy(first = it.first + it.second) }
      val height = points
        .map { it.first.second }
        .let { (it.maxOrNull() ?: 0) - (it.minOrNull() ?: 0) }
    } while (height > HEIGHT)

    return seconds
  }
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) = first + other.first to second + other.second

fun main() = SomeDay.mainify(Day10)
