package net.olegg.aoc.year2018.day10

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 10](https://adventofcode.com/2018/day/10)
 */
object Day10 : DayOf2018(10) {
  private val PATTERN = "position=<\\s*(-?\\d+),\\s*(-?\\d+)> velocity=<\\s*(-?\\d+),\\s*(-?\\d+)>".toRegex()
  private const val HEIGHT = 16

  override fun first(): Any? {
    val points = lines
      .mapNotNull { line ->
        PATTERN.find(line)?.let { match ->
          val (x, y, vx, vy) = match.destructured.toList().map { it.toInt() }
          return@let Vector2D(x, y) to Vector2D(vx, vy)
        }
      }

    val final = generateSequence(points) { curr ->
      curr.map { it.copy(first = it.first + it.second) }
    }
      .first { curr ->
        curr.map { it.first.y }.let { it.max() - it.min() } <= HEIGHT
      }

    val coords = final.map { it.first }

    val xmin = coords.minOf { it.x }
    val xmax = coords.maxOf { it.x }
    val ymin = coords.minOf { it.y }
    val ymax = coords.maxOf { it.y }

    return buildString {
      append('\n')
      (ymin..ymax).forEach { y ->
        (xmin..xmax).forEach { x ->
          append(if (Vector2D(x, y) in coords) "██" else "..")
        }
        append('\n')
      }
    }
  }

  override fun second(): Any? {
    val points = lines
      .mapNotNull { line ->
        PATTERN.find(line)?.let { match ->
          val (x, y, vx, vy) = match.destructured.toList().map { it.toInt() }
          return@let Vector2D(x, y) to Vector2D(vx, vy)
        }
      }

    val final = generateSequence(points) { curr ->
      curr.map { it.copy(first = it.first + it.second) }
    }
      .indexOfFirst { curr ->
        curr.map { it.first.y }.let { it.max() - it.min() } <= HEIGHT
      }

    return final
  }
}

fun main() = SomeDay.mainify(Day10)
