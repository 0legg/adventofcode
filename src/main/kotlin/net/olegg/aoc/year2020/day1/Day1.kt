package net.olegg.aoc.year2020.day1

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 1](https://adventofcode.com/2020/day/1)
 */
object Day1 : DayOf2020(1) {
  override fun first(data: String): Any? {
    val nums = data
      .parseLongs("\n")
      .toSet()
    val first = nums.first { (2020L - it) in nums }
    return first * (2020L - first)
  }

  override fun second(data: String): Any? {
    val nums = data
      .parseLongs("\n")
      .toSet()
    val pairs = nums.flatMapIndexed { index, first ->
      nums.drop(index + 1).map { first to it }
    }
    val pair = pairs.first { (first, second) -> (2020L - first - second) in nums }
    return pair.first * pair.second * (2020L - pair.first - pair.second)
  }
}

fun main() = SomeDay.mainify(Day1)
