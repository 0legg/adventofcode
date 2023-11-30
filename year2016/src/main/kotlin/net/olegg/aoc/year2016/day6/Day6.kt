package net.olegg.aoc.year2016.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 6](https://adventofcode.com/2016/day/6)
 */
object Day6 : DayOf2016(6) {
  override fun first(): Any? {
    return lines
      .flatMap { it.withIndex() }
      .groupBy(
        keySelector = { it.index },
        valueTransform = { it.value },
      )
      .mapValues { (_, value) -> value.groupingBy { it }.eachCount() }
      .mapValues { (_, value) -> value.maxBy { it.value }.key }
      .toList()
      .sortedBy { it.first }
      .map { it.second }
      .joinToString(separator = "")
  }

  override fun second(): Any? {
    return lines
      .flatMap { it.withIndex() }
      .groupBy(
        keySelector = { it.index },
        valueTransform = { it.value },
      )
      .mapValues { (_, value) -> value.groupingBy { it }.eachCount() }
      .mapValues { (_, value) -> value.minBy { it.value }.key }
      .toList()
      .sortedBy { it.first }
      .map { it.second }
      .joinToString(separator = "")
  }
}

fun main() = SomeDay.mainify(Day6)
