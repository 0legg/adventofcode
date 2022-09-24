package net.olegg.aoc.year2016.day12

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.AsmBunny
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 12](https://adventofcode.com/2016/day/12)
 */
object Day12 : DayOf2016(12) {
  override fun first(): Any? {
    val program = data.lines().filter { it.isNotBlank() }
    val registers = IntArray(4)

    return AsmBunny.eval(program, registers).first().toString()
  }

  override fun second(): Any? {
    val program = data.lines().filter { it.isNotBlank() }
    val registers = IntArray(4).apply { this[2] = 1 }

    return AsmBunny.eval(program, registers).first().toString()
  }
}

fun main() = SomeDay.mainify(Day12)
