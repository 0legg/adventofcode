package net.olegg.aoc.year2019.day12

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector3D
import net.olegg.aoc.year2018.day10.Day10
import net.olegg.aoc.year2019.DayOf2019
import kotlin.math.sign

/**
 * See [Year 2019, Day 12](https://adventofcode.com/2019/day/12)
 */
object Day12 : DayOf2019(12) {
  private val PATTERN = "<.=(-?\\d+), .=(-?\\d+), .=(-?\\d+)>".toRegex()
  override fun first(data: String): Any? {
    val initial = data
        .trim()
        .lines()
        .mapNotNull { line ->
          PATTERN.find(line)?.let { match ->
            val (x, y, z) = match.destructured.toList().map { it.toInt() }
            return@let Vector3D(x, y, z)
          }
        }

    val planets = initial.map { it to Vector3D() }
    repeat(1000) {
      val speedChanges = planets.map { first ->
        planets.filter { it != first }
            .map { second ->
              val diff = second.first - first.first
              Vector3D(diff.x.sign, diff.y.sign, diff.z.sign)
            }
            .fold(Vector3D()) { acc, value -> acc + value }
      }

      planets.zip(speedChanges).forEach { (planet, speedChange) ->
        planet.second += speedChange
      }

      planets.forEach { (position, speed) -> position += speed }
    }

    return planets.sumBy {
      it.first.manhattan() * it.second.manhattan()
    }
  }
}

fun main() = SomeDay.mainify(Day12)
