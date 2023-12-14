package net.olegg.aoc.year2023.day14

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 14](https://adventofcode.com/2023/day/14)
 */
object Day14 : DayOf2023(14) {
  override fun first(): Any? {
    val mat = matrix.transpose()

    return mat.sumOf { row ->
      val total = StringBuilder()
      val round = StringBuilder()
      "#$row#".reversed().forEach { char ->
        when (char) {
          '.' -> total.append(char)
          'O' -> round.append(char)
          '#' -> {
            total.append(round).append(char)
            round.setLength(0)
          }
        }
      }

      total.mapIndexed { index, c -> if (c == 'O') index else 0 }.sum()
    }
  }

  private fun List<List<Char>>.transpose(): List<List<Char>> {
    return List(first().size) { row ->
      List(size) { column -> this[column][row] }
    }
  }
}

fun main() = SomeDay.mainify(Day14)
