package net.olegg.aoc.year2021.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 6](https://adventofcode.com/2021/day/6)
 */
object Day6 : DayOf2021(6) {
  override fun first(data: String): Any? {
    val fish = data.trim().parseInts(",")
    val start = fish.groupBy { it }
      .mapValues { it.value.size }

    val end = (1 .. 80).fold(start) { acc, day ->
      val new = acc.mapKeys { it.key - 1 }.toMutableMap()
      new[8] = new.getOrDefault(-1, 0)
      new[6] = new.getOrDefault(-1, 0) + new.getOrDefault(6, 0)
      new.remove(-1)
      return@fold new.toMap()
    }

    return end.toList().sumOf { it.second }
  }
}

fun main() = SomeDay.mainify(Day6)
