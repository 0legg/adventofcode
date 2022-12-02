package net.olegg.aoc.year2022.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 2](https://adventofcode.com/2022/day/2)
 */
object Day2 : DayOf2022(2) {
  private const val abc = "ABC"
  private const val xyz = "XYZXYZ"

  override fun first(): Any? {
    return lines
      .map { line -> line.split(" ").map { it.first() }.toPair() }
      .sumOf { (first, second) ->
        xyz.indexOf(second) + 1 + when ((xyz.lastIndexOf(second) - abc.indexOf(first)) % 3) {
          1 -> 6
          0 -> 3
          else -> 0
        }
      }
  }
}

fun main() = SomeDay.mainify(Day2)
