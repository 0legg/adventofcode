package net.olegg.aoc.year2020.day9

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 9](https://adventofcode.com/2020/day/9)
 */
object Day9 : DayOf2020(9) {
  override fun first(data: String): Any? {
    val nums = data
      .trim()
      .parseInts(delimiters = "\n")

    return nums.windowed(26)
      .map { it.take(25) to it.last() }
      .map { (head, tail) -> head.flatMapIndexed { i, x -> head.drop(i + 1).map { it + x } }.toSet() to tail }
      .first { (head, tail) -> tail !in head }
      .second
  }
}

fun main() = SomeDay.mainify(Day9)
