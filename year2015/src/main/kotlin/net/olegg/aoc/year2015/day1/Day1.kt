package net.olegg.aoc.year2015.day1

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 1](https://adventofcode.com/2015/day/1)
 */
object Day1 : DayOf2015(1) {
  private val floors = data.map { 1 - 2 * (it.minus('(')) }

  override fun first(): Any? {
    return floors.sum()
  }

  override fun second(): Any? {
    return floors.runningReduce { acc, value -> acc + value }.indexOfFirst { it < 0 } + 1
  }
}

fun main() = SomeDay.mainify(Day1)
