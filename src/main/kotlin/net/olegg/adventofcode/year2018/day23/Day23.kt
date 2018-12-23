package net.olegg.adventofcode.year2018.day23

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018
import kotlin.math.abs

/**
 * See [Year 2018, Day 23](https://adventofcode.com/2018/day/23)
 */
class Day23 : DayOf2018(23) {
  companion object {
    private val PATTERN = "pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(-?\\d+)".toRegex()
  }

  override fun first(data: String): Any? {
    val bots = data
        .trim()
        .lines()
        .mapNotNull { line ->
          PATTERN.matchEntire(line)?.let { match ->
            val (x, y, z, r) = match.groupValues.drop(1).map { it.toLong() }
            return@mapNotNull Bot(x, y, z, r)
          }
        }

    val strong = bots.maxBy { it.r } ?: bots.first()

    return bots.count { strong.distance(it) <= strong.r }
  }

  data class Bot(
      val x: Long,
      val y: Long,
      val z: Long,
      val r: Long
  ) {
    fun distance(other: Bot): Long {
      return abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
    }
  }
}

fun main() = SomeDay.mainify(Day23::class)
