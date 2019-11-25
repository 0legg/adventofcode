package net.olegg.adventofcode.year2015.day1

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.utils.scan
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * See [Year 2015, Day 1](https://adventofcode.com/2015/day/1)
 */
class Day1 : DayOf2015(1) {
  val floors = data.map { 1 - 2 * (it.minus('(')) }

  override fun first(data: String): Any? {
    return floors.sum()
  }

  override fun second(data: String): Any? {
    return floors.scan(0) { acc, value -> acc + value }.indexOfFirst { it < 0 } + 1
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day1::class)
