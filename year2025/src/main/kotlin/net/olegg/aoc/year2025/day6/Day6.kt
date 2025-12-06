package net.olegg.aoc.year2025.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.utils.transpose
import net.olegg.aoc.year2025.DayOf2025

/**
 * See [Year 2025, Day 6](https://adventofcode.com/2025/day/6)
 */
object Day6 : DayOf2025(6) {
  override fun first(): Any? {
    val rawMatrix = lines.dropLast(1).map { it.parseLongs(" ") }
    val ops = lines.last().split("\\s+".toRegex())

    return rawMatrix
      .transpose()
      .mapIndexed { column, values ->
        val op: Long.(Long) -> Long = when (val rawOp = ops[column]) {
          "+" -> Long::plus
          "*" -> Long::times
          else -> error("Unknown op $rawOp")
        }
        values.reduce(op)
      }
      .sum()
  }
}

fun main() = SomeDay.mainify(Day6)
