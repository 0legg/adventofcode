package net.olegg.aoc.year2021.day22

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector3D
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2021.DayOf2021
import kotlin.math.absoluteValue

/**
 * See [Year 2021, Day 22](https://adventofcode.com/2021/day/22)
 */
object Day22 : DayOf2021(22) {
  private val pattern = "(on|off) x=(-?\\d+)\\.\\.(-?\\d+),y=(-?\\d+)\\.\\.(-?\\d+),z=(-?\\d+)\\.\\.(-?\\d+)".toRegex()

  override fun first(data: String): Any? {
    val ops = data.trim().lines()
      .mapNotNull { line ->
        val parsed = pattern.find(line)?.groupValues.orEmpty()
        if (parsed.size == 8) {
          val op = parsed[1]
          val from = Vector3D(parsed[2].toInt(), parsed[4].toInt(), parsed[6].toInt())
          val to = Vector3D(parsed[3].toInt(), parsed[5].toInt(), parsed[7].toInt())
          Triple(op, from, to)
        } else {
          null
        }
      }

    return ops
      .filterNot { (_, from, to) ->
        from.toList().any { it >= 50 } || to.toList().any { it <= -50 }
      }
      .fold(setOf<Vector3D>()) { acc, (op, from, to) ->
        val cube = (from.x..to.x).flatMap { x ->
          (from.y..to.y).flatMap { y ->
            (from.z..to.z).map { z ->
              Vector3D(x, y, z)
            }
          }
        }.toSet()

        when (op) {
          "on" -> acc + cube
          "off" -> acc - cube
          else -> acc
        }
      }
      .filterNot { cubit -> cubit.toList().any { it.absoluteValue > 50 } }
      .size
  }
}

fun main() = SomeDay.mainify(Day22)
