package net.olegg.aoc.year2020.day15

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 15](https://adventofcode.com/2020/day/15)
 */
object Day15 : DayOf2020(15) {
  override fun first(): Any? {
    return solve(2020)
  }

  override fun second(): Any? {
    return solve(30000000)
  }

  private fun solve(steps: Int): Int {
    val nums = data.trim().parseInts(delimiters = ",")

    val meets = nums.dropLast(1)
      .withIndex()
      .associateTo(mutableMapOf()) { (index, value) -> value to index + 1 }

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
