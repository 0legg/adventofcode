package net.olegg.aoc.year2015.day14

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 14](https://adventofcode.com/2015/day/14)
 */
object Day14 : DayOf2015(14) {
  private const val TIME = 2503
  private val LINE_PATTERN = ".*\\b(\\d+)\\b.*\\b(\\d+)\\b.*\\b(\\d+)\\b.*".toRegex()

  private val speeds = data
    .trim()
    .lines()
    .mapNotNull { line ->
      LINE_PATTERN.matchEntire(line)?.let { match ->
        val (speed, time, rest) = match.destructured
        return@let Triple(speed.toInt(), time.toInt(), time.toInt() + rest.toInt())
      }
    }

  override fun first(): Any? {
    return speeds
      .map { (speed, active, period) ->
        ((TIME / period) * active + (TIME % period).coerceAtMost(active)) * speed
      }
      .maxOrNull()
  }

  override fun second(): Any? {
    val distances = speeds
      .map { (speed, active, period) ->
        (0 until TIME).scan(0) { acc, value ->
          if (value % period < active) acc + speed else acc
        }.drop(1)
      }
    val timestamps = (0 until TIME)
      .map { second ->
        distances.map { it[second] }
      }
      .map { list ->
        list.map { if (it == list.maxOrNull()) 1 else 0 }
      }
    return speeds
      .indices
      .map { speed ->
        timestamps.map { it[speed] }
      }
      .map { it.sum() }
      .maxOrNull()
  }
}

fun main() = SomeDay.mainify(Day14)
