package net.olegg.adventofcode.year2015.day24

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/24">Year 2015, Day 24</a>
 */
class Day24 : DayOf2015(24) {
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

fun main(args: Array<String>) = SomeDay.mainify(Day24::class)
