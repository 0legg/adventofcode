package net.olegg.aoc.year2020.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 6](https://adventofcode.com/2020/day/6)
 */
object Day6 : DayOf2020(6) {
  override fun first(data: String): Any? {
    return data
      .trim()
      .split("\n\n")
      .map { it.replace("\n", "") }
      .map { it.toSet() }
      .sumOf { it.size }
  }

  override fun second(data: String): Any? {
    return data
      .trim()
      .split("\n\n")
      .map { it.split("\n") }
      .map { lines -> lines.map { it.toSet() }.reduce { a, b -> a.intersect(b) } }
      .sumOf { it.size }
  }
}

fun main() = SomeDay.mainify(Day6)
