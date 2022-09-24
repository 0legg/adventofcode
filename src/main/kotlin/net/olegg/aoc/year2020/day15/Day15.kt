package net.olegg.aoc.year2020.day15

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 15](https://adventofcode.com/2020/day/15)
 */
object Day15 : DayOf2020(15) {
  override fun first(): Any? {
    val nums = data.trim().parseInts(delimiters = ",")

    return solve(nums, 2020)
  }

  override fun second(): Any? {
    val nums = data.trim().parseInts(delimiters = ",")

    return solve(nums, 30000000)
  }

  private fun solve(nums: List<Int>, steps: Int): Int {
    val meets = nums.dropLast(1)
      .mapIndexed { index, value -> value to index + 1 }
      .toMap()
      .toMutableMap()
    var last = nums.last()
    var pos = nums.size
    while (pos < steps) {
      val say = pos - (meets[last] ?: pos)
      meets[last] = pos
      last = say
      pos++
    }

    return last
  }
}

fun main() = SomeDay.mainify(Day15)
