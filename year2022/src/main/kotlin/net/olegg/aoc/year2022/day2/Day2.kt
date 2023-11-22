package net.olegg.aoc.year2022.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 2](https://adventofcode.com/2022/day/2)
 */
object Day2 : DayOf2022(2) {
  private const val ABC = "ABC"
  private const val XYZ = "XYZXYZ"

  override fun first(): Any? {
    return lines
      .map { line -> line.split(" ").map { it.first() }.toPair() }
      .sumOf { (first, second) ->
        XYZ.indexOf(second) + 1 + when ((XYZ.lastIndexOf(second) - ABC.indexOf(first)) % 3) {
          1 -> 6
          0 -> 3
          else -> 0
        }
      }
  }

  override fun second(): Any? {
    return lines
      .map { line -> line.split(" ").map { it.first() }.toPair() }
      .sumOf { (first, second) ->
        XYZ.indexOf(second) * 3 + (ABC.indexOf(first) + XYZ.lastIndexOf(second) - 1) % 3 + 1
      }
  }
}

fun main() = SomeDay.mainify(Day2)
