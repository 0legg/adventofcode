package net.olegg.aoc.year2023.day4

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.findAllOverlapping
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2023.DayOf2023
import net.olegg.aoc.year2023.day2.Day2

/**
 * See [Year 2023, Day 4](https://adventofcode.com/2023/day/4)
 */
object Day4 : DayOf2023(4) {
  private val GAME_PATTERN = "Game \\d+: ".toRegex()

  override fun first(): Any? {
    return lines
      .asSequence()
      .map { line -> line.replace(GAME_PATTERN, "") }
      .map { it.split("|").toPair() }
      .map { it.first.parseInts(" ") to it.second.parseInts(" ") }
      .map { it.second.count { my -> my in it.first } }
      .sumOf { if (it == 0) 0 else (1 shl (it - 1)) }
  }
}

fun main() = SomeDay.mainify(Day4)
