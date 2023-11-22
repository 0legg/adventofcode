package net.olegg.aoc.year2015.day14

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 14](https://adventofcode.com/2015/day/14)
 */
object Day14 : DayOf2015(14) {
  private const val TIME = 2503
  private val PATTERN = ".*\\b(\\d+)\\b.*\\b(\\d+)\\b.*\\b(\\d+)\\b.*".toRegex()

  private val SPEEDS = lines
    .map { line ->
      val match = checkNotNull(PATTERN.matchEntire(line))
      val (speed, time, rest) = match.destructured
      return@map Triple(speed.toInt(), time.toInt(), time.toInt() + rest.toInt())
    }

  override fun first(): Any? {
    return SPEEDS.maxOf { (speed, active, period) ->
      ((TIME / period) * active + (TIME % period).coerceAtMost(active)) * speed
    }
  }

  override fun second(): Any? {
    val distances = SPEEDS
      .map { (speed, active, period) ->
        (0..<TIME).scan(0) { acc, value ->
          if (value % period < active) acc + speed else acc
        }.drop(1)
      }
    val timestamps = (0..<TIME)
      .map { second ->
        distances.map { it[second] }
      }
      .map { list ->
        val max = list.max()
        list.map { if (it == max) 1 else 0 }
      }
    return SPEEDS
      .indices
      .maxOf { speed ->
        timestamps.sumOf { it[speed] }
      }
  }
}

fun main() = SomeDay.mainify(Day14)
