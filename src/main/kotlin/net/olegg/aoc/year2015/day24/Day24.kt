package net.olegg.aoc.year2015.day24

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 24](https://adventofcode.com/2015/day/24)
 */
object Day24 : DayOf2015(24) {
  val weights = data.lines().map { it.toLong() }
  override fun first(data: String): Any? {
    val sum = weights.sum() / 3
    return subsets(sum, weights)
        .groupBy { it.size }
        .minBy { it.key }
        ?.value
        ?.map { it.fold(1L, Long::times) }
        ?.min()
  }

  override fun second(data: String): Any? {
    val sum = weights.sum() / 4
    return subsets(sum, weights)
        .groupBy { it.size }
        .minBy { it.key }
        ?.value
        ?.map { it.fold(1L, Long::times) }
        ?.min()
  }
}

fun subsets(sum: Long, list: List<Long>): List<List<Long>> = when {
  (sum < 0L) -> listOf()
  (sum == 0L) -> listOf(listOf())
  (list.size == 1) -> if (sum == list[0]) listOf(list) else listOf()
  else -> subsets(sum, list.drop(1)) + subsets(sum - list[0], list.drop(1)).map { listOf(list[0]) + it }
}

fun main() = SomeDay.mainify(Day24)
