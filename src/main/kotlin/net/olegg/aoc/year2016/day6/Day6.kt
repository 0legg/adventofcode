package net.olegg.aoc.year2016.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 6](https://adventofcode.com/2016/day/6)
 */
object Day6 : DayOf2016(6) {
  override fun first(): Any? {
    return data
      .lines()
      .flatMap { it.toCharArray().mapIndexed { i, c -> i to c } }
      .groupBy { it.first }
      .mapValues { (_, value) -> value.map { it.second } }
      .mapValues { (_, value) -> value.groupBy { it } }
      .mapValues { (_, value) -> value.mapValues { it.value.size } }
      .mapValues { (_, value) -> value.maxByOrNull { it.value }?.key ?: '?' }
      .toList()
      .sortedBy { it.first }
      .map { it.second }
      .joinToString(separator = "")
  }

  override fun second(): Any? {
    return data
      .lines()
      .flatMap { it.toCharArray().mapIndexed { i, c -> i to c } }
      .groupBy { it.first }
      .mapValues { (_, value) -> value.map { it.second } }
      .mapValues { (_, value) -> value.groupBy { it } }
      .mapValues { (_, value) -> value.mapValues { it.value.size } }
      .mapValues { (_, value) -> value.minByOrNull { it.value }?.key ?: '?' }
      .toList()
      .sortedBy { it.first }
      .map { it.second }
      .joinToString(separator = "")
  }
}

fun main() = SomeDay.mainify(Day6)
