package net.olegg.aoc.year2022.day3

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 3](https://adventofcode.com/2022/day/3)
 */
object Day3 : DayOf2022(3) {

  override fun first(): Any? {
    return lines
      .map { line ->
        line.take(line.length / 2) to line.drop(line.length / 2)
      }
      .map { (first, second) ->
        first.toSet().intersect(second.toSet())
      }
      .sumOf {
        when (val char = it.first()) {
          in 'a'..'z' -> char - 'a' + 1
          in 'A'..'Z' -> char - 'A' + 27
          else -> 0
        }
      }
  }
}

fun main() = SomeDay.mainify(Day3)
