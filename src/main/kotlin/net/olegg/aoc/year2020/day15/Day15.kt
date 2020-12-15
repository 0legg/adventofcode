package net.olegg.aoc.year2020.day15

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 15](https://adventofcode.com/2020/day/15)
 */
object Day15 : DayOf2020(15) {
  override fun first(data: String): Any? {
    val nums = data.trim().parseInts(delimiters = ",")

    val said = nums.toMutableList()
    while (said.size < 2020) {
      val lastMet = said.dropLast(1).lastIndexOf(said.last())
      said += if (lastMet == -1) 0 else said.size - lastMet - 1
    }

    return said.last()
  }
}

fun main() = SomeDay.mainify(Day15)
