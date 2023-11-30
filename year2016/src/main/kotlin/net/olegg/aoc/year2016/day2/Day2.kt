package net.olegg.aoc.year2016.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 2](https://adventofcode.com/2016/day/2)
 */
object Day2 : DayOf2016(2) {
  private val KEYPAD1 = listOf(
    "00000",
    "01230",
    "04560",
    "07890",
    "00000",
  ).map { it.toList() }

  private val KEYPAD2 = listOf(
    "0000000",
    "0001000",
    "0023400",
    "0567890",
    "00ABC00",
    "000D000",
    "0000000",
  ).map { it.toList() }

  override fun first(): Any? {
    return solve(KEYPAD1, Vector2D(2, 2))
  }

  override fun second(): Any? {
    return solve(KEYPAD2, Vector2D(2, 4))
  }

  private fun solve(
    keypad: List<List<Char>>,
    start: Vector2D
  ): String {
    return lines.fold("" to start) { (acc, position), command ->
      val point = command.fold(position) { accPosition, symbol ->
        (accPosition + Directions.valueOf(symbol.toString()).step).let {
          if (keypad[it] != '0') it else accPosition
        }
      }
      acc + keypad[point] to point
    }.first
  }
}

fun main() = SomeDay.mainify(Day2)
