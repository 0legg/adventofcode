package net.olegg.aoc.year2021.day17

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2021.DayOf2021
import kotlin.math.sign

/**
 * See [Year 2021, Day 17](https://adventofcode.com/2021/day/17)
 */
object Day17 : DayOf2021(17) {
  private val PATTERN = "target area: x=(-?\\d+)\\.\\.(-?\\d+), y=(-?\\d+)\\.\\.(-?\\d+)".toRegex()

  override fun first(): Any? {
    val (fromX, toX, fromY, toY) = PATTERN.find(data)?.groupValues.orEmpty().drop(1).map { it.toInt() }

    var best = fromY
    (0..toX).forEach { x ->
      (-100..100).forEach { y ->
        val positions = generateSequence(Vector2D(0, 0) to Vector2D(x, y)) { (pos, speed) ->
          val nextPos = pos + speed
          val nextSpeed = Vector2D(
            x = speed.x - speed.x.sign,
            y = speed.y - 1,
          )
          when {
            speed.x == 0 && pos.y < fromY -> null
            else -> nextPos to nextSpeed
          }
        }.map { it.first }.toList()

        if (positions.any { it.x in fromX..toX && it.y in fromY..toY }) {
          best = maxOf(best, positions.maxOf { it.y })
        }
      }
    }
    return best
  }

  override fun second(): Any? {
    val (fromX, toX, fromY, toY) = PATTERN.find(data)?.groupValues.orEmpty().drop(1).map { it.toInt() }

    return (0..toX).sumOf { x ->
      (-100..100).sumOf { y ->
        val positions = generateSequence(Vector2D(0, 0) to Vector2D(x, y)) { (pos, speed) ->
          val nextPos = pos + speed
          val nextSpeed = Vector2D(
            x = speed.x - speed.x.sign,
            y = speed.y - 1,
          )
          when {
            speed.x == 0 && pos.y < fromY -> null
            else -> nextPos to nextSpeed
          }
        }.map { it.first }.toList()

        if (positions.any { it.x in fromX..toX && it.y in fromY..toY }) {
          1L
        } else {
          0L
        }
      }
    }
  }
}

fun main() = SomeDay.mainify(Day17)
