package net.olegg.aoc.year2021.day14

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 14](https://adventofcode.com/2021/day/14)
 */
object Day14 : DayOf2021(14) {
  override fun first(data: String): Any? {
    val (start, rawPatterns) = data.trim().split("\n\n")
    val patterns = rawPatterns.lines()
      .map { it.split(" -> ") }
      .map { it.first() to it.last() }
      .map { it.first to "${it.first.first()}${it.second}${it.first.last()}" }
      .toMap()

    val end = (1..10).fold(start) { acc, _ ->
      acc.windowed(2)
        .map { patterns[it] ?: it }
        .mapIndexed { index, str -> if (index == 0) str else str.drop(1) }
        .joinToString("")
    }

    val counts = end.groupBy { it }.mapValues { it.value.size }.toList()
    return counts.maxOf { it.second } - counts.minOf { it.second }
  }
}

fun main() = SomeDay.mainify(Day14)
