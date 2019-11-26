package net.olegg.aoc.year2016.day23

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.AsmBunny
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 23](https://adventofcode.com/2016/day/23)
 */
class Day23 : DayOf2016(23) {
  override fun first(data: String): Any? {
    val program = data.lines().filter { it.isNotBlank() }
    val registers = IntArray(4).apply { this[0] = 7 }

    return AsmBunny.eval(program, registers).first().toString()
  }

  override fun second(data: String): Any? {
    val program = data.lines().filter { it.isNotBlank() }
    val registers = IntArray(4).apply { this[0] = 12 }

    return AsmBunny.eval(program, registers).first().toString()
  }
}

fun main() = SomeDay.mainify(Day23::class)
