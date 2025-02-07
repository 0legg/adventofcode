package net.olegg.aoc.year2024.day14

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 14](https://adventofcode.com/2024/day/14)
 */
object Day14 : DayOf2024(14) {
  private val digits = "(-?\\d+)".toRegex()
  private const val width = 101
  private const val height = 103

  override fun first(): Any? {
    val startRobots = lines
      .map { line -> digits.findAll(line).map { it.value.toInt() }.toList() }
      .map { (px, py, vx, vy) -> Vector2D(px, py) to Vector2D(vx, vy) }

    val final = (1..100).fold(startRobots) { robots, _ ->
      robots.map { (position, speed) ->
        Vector2D(
          (position.x + speed.x + width) % width,
          (position.y + speed.y + height) % height,
        ) to speed
      }
    }

    return final.count { it.first.x < width / 2 && it.first.y < height / 2 }.toLong() *
      final.count { it.first.x < width / 2 && it.first.y > height / 2 }.toLong() *
      final.count { it.first.x > width / 2 && it.first.y < height / 2 }.toLong() *
      final.count { it.first.x > width / 2 && it.first.y > height / 2 }.toLong()
  }

  override fun second(): Any? {
    val startData = lines.map { line -> digits.findAll(line).map { it.value.toInt() }.toList() }
    val positions = startData.map { (px, py, _, _) -> Vector2D(px, py) }
    val speeds = startData.map { (_, _, vx, vy) -> Vector2D(vx, vy) }

    return generateSequence(positions) { robots ->
      robots.zip(speeds) { position, speed ->
        Vector2D(
          (position.x + speed.x + width) % width,
          (position.y + speed.y + height) % height,
        )
      }
    }
      .map { robots ->
        buildString(height * (width * 2 + 2)) {
          (0 until height).forEach { y ->
            (0 until width).forEach { x ->
              if (robots.any { it.y == y && it.x == x }) append("██") else append("..")
            }
            append("\n")
          }
        }
      }
      .indexOfFirst { "██████████████████████████████████████████████████████████████" in it }
  }
}

fun main() = SomeDay.mainify(Day14)
