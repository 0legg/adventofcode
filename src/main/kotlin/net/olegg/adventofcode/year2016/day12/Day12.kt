package net.olegg.adventofcode.year2016.day12

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.AsmBunny
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/12">Year 2016, Day 12</a>
 */
class Day12 : DayOf2016(12) {
  override fun first(data: String): Any? {
    val program = data.lines().filter { it.isNotBlank() }
    val registers = IntArray(4)

    return AsmBunny.eval(program, registers).first().toString()
  }

  override fun second(data: String): Any? {
    val program = data.lines().filter { it.isNotBlank() }
    val registers = IntArray(4).apply { this[2] = 1 }

    return AsmBunny.eval(program, registers).first().toString()
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day12::class)
