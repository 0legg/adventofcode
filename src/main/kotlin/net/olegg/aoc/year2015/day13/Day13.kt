package net.olegg.aoc.year2015.day13

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.permutations
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 13](https://adventofcode.com/2015/day/13)
 */
object Day13 : DayOf2015(13) {
  private val pattern = "^\\b(\\w+)\\b.*\\b(gain|lose) \\b(\\d+)\\b.*\\b(\\w+)\\b\\.$".toRegex()

  private val edges = data.trim().lines().mapNotNull { line ->
    pattern.matchEntire(line)?.let { match ->
      val (name, type, amount, otherName) = match.destructured
      Pair(name, otherName) to amount.toInt() * (if (type == "gain") 1 else -1)
    }
  }.toMap()
  private val names = edges.keys.flatMap { listOf(it.first, it.second) }.distinct()

  override fun first(data: String): Any? {
    return names
      .permutations()
      .map { it + it.first() }
      .map { order ->
        order.zipWithNext().sumBy { edges[it] ?: 0 } +
          order.reversed().zipWithNext().sumBy { edges[it] ?: 0 }
      }
      .maxOrNull()
  }

  override fun second(data: String): Any? {
    return names
      .permutations()
      .map { order ->
        order.zipWithNext().sumBy { edges[it] ?: 0 } +
          order.reversed().zipWithNext().sumBy { edges[it] ?: 0 }
      }
      .maxOrNull()
  }
}

fun main() = SomeDay.mainify(Day13)
