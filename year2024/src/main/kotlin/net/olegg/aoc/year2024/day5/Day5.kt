package net.olegg.aoc.year2024.day5

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.pairs
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 5](https://adventofcode.com/2024/day/5)
 */
object Day5 : DayOf2024(5) {
  override fun first(): Any? {
    val (rulesRaw, updatesRaw) = data.split("\n\n")

    val rules = rulesRaw
      .lines()
      .map { it.parseInts("|").toPair() }
      .toSet()

    val updates = updatesRaw
      .lines()
      .map { it.parseInts(",") }

    return updates
      .filter { update ->
        update.pairs().none { (it.second to it.first) in rules }
      }
      .sumOf { it[it.size / 2] }
  }
}

fun main() = SomeDay.mainify(Day5)
