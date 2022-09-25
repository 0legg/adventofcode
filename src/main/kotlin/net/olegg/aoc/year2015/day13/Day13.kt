package net.olegg.aoc.year2015.day13

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.permutations
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 13](https://adventofcode.com/2015/day/13)
 */
object Day13 : DayOf2015(13) {
  private val pattern = "^\\b(\\w+)\\b.*\\b(gain|lose) \\b(\\d+)\\b.*\\b(\\w+)\\b\\.$".toRegex()

  private val edges = lines.associate { line ->
    val match = checkNotNull(pattern.matchEntire(line))
    val (name, type, amount, otherName) = match.destructured
    Pair(name, otherName) to amount.toInt() * (if (type == "gain") 1 else -1)
  }
  private val names = edges.keys.flatMap { listOf(it.first, it.second) }.distinct()

  override fun first(): Any? {
    return names
      .permutations()
      .map { it + it.first() }
      .map { order ->
        order.zipWithNext().sumOf { edges[it] ?: 0 } +
          order.reversed().zipWithNext().sumOf { edges[it] ?: 0 }
      }
      .maxOrNull()
  }

  override fun second(): Any? {
    return names
      .permutations()
      .map { order ->
        order.zipWithNext().sumOf { edges[it] ?: 0 } +
          order.reversed().zipWithNext().sumOf { edges[it] ?: 0 }
      }
      .maxOrNull()
  }
}

fun main() = SomeDay.mainify(Day13)
