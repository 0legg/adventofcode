package net.olegg.aoc.year2016.day25

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.AsmBunny
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 25](https://adventofcode.com/2016/day/25)
 */
object Day25 : DayOf2016(25) {
  override fun first(): Any? {
    val program = data.trim().lines()

    return generateSequence(0) { it + 1 }
      .map { value ->
        val registers = IntArray(4).also { it[0] = value }
        return@map value to AsmBunny.eval(program, registers)
      }
      .first { (_, signal) ->
        signal
          .take(1000)
          .mapIndexed { index, value -> index % 2 == value % 2 }
          .all { it }
      }
      .first
  }
}

fun main() = SomeDay.mainify(Day25)
