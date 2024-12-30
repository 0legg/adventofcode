package net.olegg.aoc.year2024.day25

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.transpose
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 25](https://adventofcode.com/2024/day/25)
 */
object Day25 : DayOf2024(25) {
  override fun first(): Any? {
    val rawSets = data.split("\n\n")
    val (rawLocks, rawKeys) = rawSets.partition { it.startsWith("#") }

    val locks = rawLocks.map { rawLock ->
      rawLock.lines()
        .map { it.toList() }
        .transpose()
        .map { column -> column.count { it == '#' } }
    }

    val keys = rawKeys.map { rawKey ->
      rawKey.lines()
        .map { it.toList() }
        .transpose()
        .map { column -> column.count { it == '#' } }
    }

    val size = rawLocks.first().lines().count()

    return locks.sumOf { lock ->
      keys.count { key ->
        lock.zip(key).all { it.first + it.second <= size }
      }
    }
  }
}

fun main() = SomeDay.mainify(Day25)
