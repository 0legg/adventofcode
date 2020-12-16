package net.olegg.aoc.year2020.day16

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 16](https://adventofcode.com/2020/day/16)
 */
object Day16 : DayOf2020(16) {
  private val RANGE_PATTERN = ".*: (\\d+)-(\\d+) or (\\d+)-(\\d+)".toRegex()
  override fun first(data: String): Any? {
    val (ranges, yours, nearby) = data
      .trim()
      .split("\n\n")

    val valid = mutableSetOf<Int>()

    ranges
      .lines()
      .flatMap { line -> RANGE_PATTERN.find(line)?.groupValues?.drop(1).orEmpty() }
      .windowed(2)
      .map { it.toPair() }
      .forEach { (from, to) -> (from.toInt()..to.toInt()).forEach { valid += it } }

    val invalid = mutableListOf<Int>()
    invalid += yours
      .lines()
      .drop(1)
      .flatMap { it.parseInts(",") }
      .filter { it !in valid }

    invalid += nearby
      .lines()
      .drop(1)
      .flatMap { it.parseInts(",") }
      .filter { it !in valid }

    return invalid.sum()
  }
}

fun main() = SomeDay.mainify(Day16)
