package net.olegg.aoc.year2022.day15

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 15](https://adventofcode.com/2022/day/15)
 */
object Day15 : DayOf2022(15) {
  private val pattern = "^Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)$".toRegex()
  override fun first(): Any? {
    val pairs = lines
      .mapNotNull { pattern.find(it) }
      .map { match -> match.destructured.toList().map { it.toInt() } }
      .map { coords ->
        val sensor = Vector2D(coords[0], coords[1])
        val beacon = Vector2D(coords[2], coords[3])
        Triple(sensor, beacon, (sensor - beacon).manhattan())
      }

    val beacons = pairs.map { it.second }.toSet()

    val maxDist = pairs.maxOf { it.third } + 1

    val minX = pairs.minOf { minOf(it.first.x, it.second.x) } - maxDist
    val maxX = pairs.maxOf { maxOf(it.first.x, it.second.x) } + maxDist
    val y = 2000000

    return (minX..maxX)
      .asSequence()
      .map { Vector2D(it, y) }
      .filterNot { it in beacons }
      .count { point ->
        pairs.any {
          (it.first - point).manhattan() <= it.third
        }
      }
  }
}

fun main() = SomeDay.mainify(Day15)
