package net.olegg.aoc.year2016.day23

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.AsmBunny
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 23](https://adventofcode.com/2016/day/23)
 */
object Day23 : DayOf2016(23) {
  override fun first(): Any? {
    val registers = IntArray(4).apply { this[0] = 7 }

    return AsmBunny.eval(lines, registers).first()
  }

  override fun second(): Any? {
    val registers = IntArray(4).apply { this[0] = 12 }

    return AsmBunny.eval(lines, registers).first()
  }
}

fun main() = SomeDay.mainify(Day23)
