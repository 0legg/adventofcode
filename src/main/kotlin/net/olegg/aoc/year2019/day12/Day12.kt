package net.olegg.aoc.year2019.day12

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector3D
import net.olegg.aoc.utils.lcf
import net.olegg.aoc.year2019.DayOf2019
import kotlin.math.sign

/**
 * See [Year 2019, Day 12](https://adventofcode.com/2019/day/12)
 */
object Day12 : DayOf2019(12) {
  private val PATTERN = "<.=(-?\\d+), .=(-?\\d+), .=(-?\\d+)>".toRegex()
  override fun first(): Any? {
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
      planets.map { first ->
        planets.forEach { second ->
          val diff = second.first - first.first
          first.second += Vector3D(diff.x.sign, diff.y.sign, diff.z.sign)
        }
      }

      planets.forEach { (position, speed) -> position += speed }
    }

    return planets.sumOf {
      it.first.manhattan() * it.second.manhattan()
    }
  }

  override fun second(): Any? {
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
    val footprints = List(3) { mutableMapOf<List<Int>, Long>() }
    val repeats = mutableMapOf<Int, Long>()
    var step = 0L
    while (repeats.size < footprints.size) {
      val curr = footprints.indices.map { axis ->
        planets.flatMap { (planet, speed) -> listOf(planet[axis], speed[axis]) }
      }
      curr.forEachIndexed { axis, footprint ->
        if (axis !in repeats) {
          if (footprint in footprints[axis]) {
            repeats[axis] = step - (footprints[axis][footprint] ?: 0L)
          } else {
            footprints[axis][footprint] = step
          }
        }
      }
      step++
      planets.map { first ->
        planets.forEach { second ->
          val diff = second.first - first.first
          first.second += Vector3D(diff.x.sign, diff.y.sign, diff.z.sign)
        }
      }

      planets.forEach { (position, speed) -> position += speed }
    }

    return repeats.values.reduce { a, b -> lcf(a, b) }
  }
}

fun main() = SomeDay.mainify(Day12)
