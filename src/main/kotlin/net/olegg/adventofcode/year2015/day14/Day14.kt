package net.olegg.adventofcode.year2015.day14

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.utils.scan
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/14">Year 2015, Day 14</a>
 */
class Day14 : DayOf2015(14) {
  companion object {
    const val TIME = 2503
    val LINE_PATTERN = ".*\\b(\\d+)\\b.*\\b(\\d+)\\b.*\\b(\\d+)\\b.*".toRegex()
  }

  private val speeds = data
      .trim()
      .lines()
      .mapNotNull { line ->
        LINE_PATTERN.matchEntire(line)?.let { match ->
          val (speed, time, rest) = match.destructured
          return@let Triple(speed.toInt(), time.toInt(), time.toInt() + rest.toInt())
        }
      }

  override fun first(data: String): Any? {
    return speeds
        .map { (speed, active, period) ->
          ((TIME / period) * active + (TIME % period).coerceAtMost(active)) * speed
        }
        .max()
  }

  override fun second(data: String): Any? {
    val distances = speeds
        .map { (speed, active, period) ->
          (0 until TIME).scan(0) { acc, value ->
            if (value % period < active) acc + speed else acc
          }
        }
    val timestamps = (0 until TIME)
        .map { second ->
          distances.map { it[second] }
        }
        .map { list ->
          list.map { if (it == list.max()) 1 else 0 }
        }
    return speeds
        .indices
        .map { speed ->
          timestamps.map { it[speed] }
        }
        .map { it.sum() }
        .max()
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day14::class)
