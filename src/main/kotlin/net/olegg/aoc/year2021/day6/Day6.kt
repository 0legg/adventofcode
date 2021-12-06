package net.olegg.aoc.year2021.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 6](https://adventofcode.com/2021/day/6)
 */
object Day6 : DayOf2021(6) {
  override fun first(data: String): Any? {
    val fish = data.trim().parseLongs(",")

    return solve(fish, 80)
  }

  override fun second(data: String): Any? {
    val fish = data.trim().parseLongs(",")

    return solve(fish, 256)
  }

  private fun solve(input: List<Long>, days: Int): Long {
    val start = input.groupBy { it }
      .mapValues { it.value.size.toLong() }
    val end = (1..days).fold(start) { acc, _ ->
      val new = acc.mapKeys { it.key - 1 }.toMutableMap()
      new[8] = new.getOrDefault(-1, 0L)
      new[6] = new.getOrDefault(-1, 0L) + new.getOrDefault(6, 0L)
      new.remove(-1)
      return@fold new.toMap()
    }

    return end.toList().sumOf { it.second }
  }
}

fun main() = SomeDay.mainify(Day6)
