package net.olegg.aoc.year2015.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 2](https://adventofcode.com/2015/day/2)
 */
object Day2 : DayOf2015(2) {
  private val boxes = data.trim().lines().map { it.parseInts("x").sorted() }

  override fun first(data: String): Any? {
    return boxes.sumBy { 3 * it[0] * it[1] + 2 * it[0] * it[2] + 2 * it[1] * it[2] }.toString()
  }

  override fun second(data: String): Any? {
    return boxes.sumBy { 2 * (it[0] + it[1]) + it[0] * it[1] * it[2] }.toString()
  }
}

fun main() = SomeDay.mainify(Day2)
