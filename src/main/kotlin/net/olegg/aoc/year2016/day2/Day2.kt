package net.olegg.aoc.year2016.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Vector2D
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
      "00000"
  )

  private val KEYPAD2 = listOf(
      "0000000",
      "0001000",
      "0023400",
      "0567890",
      "00ABC00",
      "000D000",
      "0000000"
  )

  override fun first(data: String): Any? {
    return solve(data.trim().lines(), KEYPAD1, Vector2D(2, 2))
  }

  override fun second(data: String): Any? {
    return solve(data.trim().lines(), KEYPAD2, Vector2D(2, 4))
  }

  private fun solve(commands: List<String>, keypad: List<String>, start: Vector2D): String {
    return commands.fold("" to start) { (acc, position), command ->
      val point = command.toCharArray().fold(position) { accPosition, symbol ->
        (accPosition + Directions.valueOf(symbol.toString()).step).let {
          if (keypad[it.y][it.x] != '0') it else accPosition
        }
      }
      acc + "${keypad[point.y][point.x]}" to point
    }.first
  }
}

fun main() = SomeDay.mainify(Day2)
