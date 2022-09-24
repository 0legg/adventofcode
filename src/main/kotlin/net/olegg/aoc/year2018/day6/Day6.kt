package net.olegg.aoc.year2018.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018
import kotlin.math.abs

/**
 * See [Year 2018, Day 6](https://adventofcode.com/2018/day/6)
 */
object Day6 : DayOf2018(6) {
  private val PATTERN = "(\\d+), (\\d+)".toRegex()
  private const val TOTAL = 10000

  override fun first(): Any? {
    val points = data
      .trim()
      .lines()
      .mapNotNull { line ->
        PATTERN.matchEntire(line)
          ?.destructured
          ?.toList()
          ?.map { it.toInt() }
          ?.let { it.first() to it.last() }
      }

    val left = points.minOfOrNull { it.first } ?: Int.MIN_VALUE
    val top = points.minOfOrNull { it.second } ?: Int.MIN_VALUE
    val right = points.maxOfOrNull { it.first } ?: Int.MAX_VALUE
    val bottom = points.maxOfOrNull { it.second } ?: Int.MAX_VALUE

    val area = (top..bottom)
      .flatMap { y ->
        (left..right).mapNotNull { x ->
          val dist = points.map { abs(x - it.first) + abs(y - it.second) }
          val best = dist.minOrNull() ?: 0
          if (dist.count { it == best } == 1) dist.indexOfFirst { it == best } else null
        }
      }

    return area
      .groupBy { it }
      .mapValues { it.value.size }
      .filterKeys { key ->
        points[key].first !in listOf(left, right) && points[key].second !in listOf(top, bottom)
      }
      .maxByOrNull { it.value }
      ?.value
  }

  override fun second(): Any? {
    val points = data
      .trim()
      .lines()
      .mapNotNull { line ->
        PATTERN.matchEntire(line)
          ?.destructured
          ?.toList()
          ?.map { it.toInt() }
          ?.let { it.first() to it.last() }
      }

    val left = points.minOfOrNull { it.first } ?: Int.MIN_VALUE
    val top = points.minOfOrNull { it.second } ?: Int.MIN_VALUE
    val right = points.maxOfOrNull { it.first } ?: Int.MAX_VALUE
    val bottom = points.maxOfOrNull { it.second } ?: Int.MAX_VALUE

    val padding = TOTAL / points.size + 1

    return (top - padding..bottom + padding)
      .flatMap { y ->
        (left - padding..right + padding).map { x ->
          points.sumOf { abs(x - it.first) + abs(y - it.second) }
        }
      }
      .count { it < TOTAL }
  }
}

fun main() = SomeDay.mainify(Day6)
