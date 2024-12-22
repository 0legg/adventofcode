package net.olegg.aoc.year2024.day22

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 22](https://adventofcode.com/2024/day/22)
 */
object Day22 : DayOf2024(22) {
  private val modulo = 16777215L

  override fun first(): Any? {
    return lines.map { it.toLong() }
      .sumOf { number ->
        generateSequence(number) { curr ->
          val first = (curr xor (curr shl 6)) and modulo
          val second = (first xor (first shr 5)) and modulo
          val third = (second xor (second shl 11)) and modulo
          third
        }
          .drop(2000)
          .first()
      }
  }
}

fun main() = SomeDay.mainify(Day22)
