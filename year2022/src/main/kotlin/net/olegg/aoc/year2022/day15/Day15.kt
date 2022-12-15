package net.olegg.aoc.year2022.day15

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2022.DayOf2022
import kotlin.math.abs

/**
 * See [Year 2022, Day 15](https://adventofcode.com/2022/day/15)
 */
object Day15 : DayOf2022(15) {
  private val pattern = "^Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)$".toRegex()
  override fun first(): Any? {
    val tuples = lines
      .mapNotNull { pattern.find(it) }
      .map { match -> match.destructured.toList().map { it.toInt() } }
      .map { coords ->
        val sensor = Vector2D(coords[0], coords[1])
        val beacon = Vector2D(coords[2], coords[3])
        Triple(sensor, beacon, (sensor - beacon).manhattan())
      }

    val beacons = tuples.map { it.second }.toSet()
    val sensors = tuples.map { it.first to it.third }

    val maxDist = tuples.maxOf { it.third } + 1

    val minX = tuples.minOf { minOf(it.first.x, it.second.x) } - maxDist
    val maxX = tuples.maxOf { maxOf(it.first.x, it.second.x) } + maxDist
    val y = 2000000

    return (minX..maxX)
      .asSequence()
      .map { Vector2D(it, y) }
      .filterNot { it in beacons }
      .count { point ->
        sensors.any {
          (it.first - point).manhattan() <= it.second
        }
      }
  }

  override fun second(): Any? {
    val tuples = lines
      .mapNotNull { pattern.find(it) }
      .map { match -> match.destructured.toList().map { it.toInt() } }
      .map { coords ->
        val sensor = Vector2D(coords[0], coords[1])
        val beacon = Vector2D(coords[2], coords[3])
        Triple(sensor, beacon, (sensor - beacon).manhattan())
      }

    val beacons = tuples.map { it.second }.toSet()
    val sensors = tuples.map { it.first to it.third }

    val from = 0
    val to = 4000000
    val range = (from..to)

    return range
      .asSequence()
      .mapNotNull { y ->
        val events = sensors
          .flatMap { sensor ->
            val dy = abs(sensor.first.y - y)
            val dx = sensor.second - dy
            if (dx >= 0) {
              listOf(
                Pair(sensor.first.x - dx, 1),
                Pair(sensor.first.x + dx + 1, -1),
              )
            } else {
              emptyList()
            }
          }
          .sortedBy { it.first }

        events.drop(1)
          .asSequence()
          .runningFold(events.first()) { acc, value ->
            value.first to acc.second + value.second
          }
          .firstOrNull { it.second == 0 }
          ?.takeIf { it.first in range }
          ?.let { Vector2D(it.first, y) }
          ?.takeIf { it !in beacons }
      }
      .first()
      .let { it.x * 4000000L + it.y }
  }
}

fun main() = SomeDay.mainify(Day15)
