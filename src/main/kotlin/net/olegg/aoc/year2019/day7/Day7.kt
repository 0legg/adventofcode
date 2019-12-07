package net.olegg.aoc.year2019.day7

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.permutations
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

/**
 * See [Year 2019, Day 7](https://adventofcode.com/2019/day/7)
 */
object Day7 : DayOf2019(7) {
  override fun first(data: String): Any? {
    val program = data
        .trim()
        .parseInts(",")
        .toIntArray()

    val phases = List(5) { it }

    return phases.permutations()
        .map { orderedPhases ->
          orderedPhases.fold(0) { acc, phase ->
            val curr = program.copyOf()
            val (output) = Intcode.eval(curr, listOf(phase, acc))
            return@fold output
          }
        }
        .max()
  }
}

fun main() = SomeDay.mainify(Day7)
