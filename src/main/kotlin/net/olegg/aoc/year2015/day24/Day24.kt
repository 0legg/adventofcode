package net.olegg.aoc.year2015.day24

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 24](https://adventofcode.com/2015/day/24)
 */
object Day24 : DayOf2015(24) {
  private val weights = data.parseLongs(delimiters = "\n")

  override fun first(): Any? {
    val sum = weights.sum() / 3
    return subsets(sum, weights)
      .groupBy { it.size }
      .minBy { it.key }
      .value
      .minOf { it.fold(1L, Long::times) }
  }

  override fun second(): Any? {
    val sum = weights.sum() / 4
    return subsets(sum, weights)
      .groupBy { it.size }
      .minBy { it.key }
      .value
      .minOf { it.fold(1L, Long::times) }
  }
}

fun subsets(sum: Long, list: List<Long>): List<List<Long>> = when {
  (sum < 0L) -> emptyList()
  (sum == 0L) -> listOf(emptyList())
  (list.size == 1) -> if (sum == list[0]) listOf(list) else emptyList()
  else -> subsets(sum, list.drop(1)) + subsets(sum - list[0], list.drop(1)).map { listOf(list[0]) + it }
}

fun main() = SomeDay.mainify(Day24)
