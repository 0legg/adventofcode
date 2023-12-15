package net.olegg.aoc.year2023.day15

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 15](https://adventofcode.com/2023/day/15)
 */
object Day15 : DayOf2023(15) {
  override fun first(): Any? {
    return data.split(",").sumOf { step ->
      step.fold(0) { acc, char ->
        (acc + char.code) * 17 % 256
      }.toInt()
    }
  }
}

fun main() = SomeDay.mainify(Day15)
