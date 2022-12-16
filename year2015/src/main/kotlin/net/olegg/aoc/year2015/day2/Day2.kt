package net.olegg.aoc.year2015.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 2](https://adventofcode.com/2015/day/2)
 */
object Day2 : DayOf2015(2) {
  private val boxes = lines.map { it.parseInts("x").sorted() }

  override fun first(): Any? {
    return boxes.sumOf { 3 * it[0] * it[1] + 2 * it[0] * it[2] + 2 * it[1] * it[2] }.toString()
  }

  override fun second(): Any? {
    return boxes.sumOf { 2 * (it[0] + it[1]) + it[0] * it[1] * it[2] }.toString()
  }
}

fun main() = SomeDay.mainify(Day2)
