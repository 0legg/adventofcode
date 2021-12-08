package net.olegg.aoc.year2021.day8

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 8](https://adventofcode.com/2021/day/8)
 */
object Day8 : DayOf2021(8) {
  override fun first(data: String): Any? {
    return data.trim()
      .lines()
      .map { it.split("|").last().trim() }
      .flatMap { it.split(" ") }
      .count { it.length in setOf(2, 3, 4, 7) }
  }
}

fun main() = SomeDay.mainify(Day8)
