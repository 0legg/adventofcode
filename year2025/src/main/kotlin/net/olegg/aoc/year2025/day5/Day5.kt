package net.olegg.aoc.year2025.day5

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2025.DayOf2025

/**
 * See [Year 2025, Day 5](https://adventofcode.com/2025/day/5)
 */
object Day5 : DayOf2025(5) {
  override fun first(): Any? {
    val (rawRanges, rawElements) = data.split("\n\n")

    val ranges = rawRanges
      .lines()
      .map { it.parseLongs("-") }
      .map { it.first()..it.last() }

    val elements = rawElements.parseLongs("\n")

    return elements.count { element ->
      ranges.any { element in it }
    }
  }

  override fun second(): Any? {
    val events = lines
      .takeWhile { it.isNotEmpty() }
      .map { it.parseLongs("-") }
      .flatMap { listOf(it.first() to 1, it.last() to -1) }
      .sortedWith(compareBy({ it.first }, { -it.second }))

    return events.fold(Triple(events.first().first - 1, 0, 0L)) { (start, depth, score), (pos, shift) ->
      val newDepth = depth + shift
      when {
        depth == 0 -> Triple(pos, newDepth, score)
        newDepth == 0 -> Triple(pos, newDepth, score + (pos - start + 1))
        else -> Triple(start, newDepth, score)
      }
    }.third
  }
}

fun main() = SomeDay.mainify(Day5)
