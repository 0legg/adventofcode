package net.olegg.aoc.year2016.day8

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 8](https://adventofcode.com/2016/day/8)
 */
object Day8 : DayOf2016(8) {
  private val PATTERN = "(\\D*)(\\d+)\\D*(\\d+)\\D*".toRegex()

  override fun first(): Any? {
    return applyOperations()
      .sumOf { row -> row.count { it } }
  }

  override fun second(): Any? {
    return applyOperations()
      .joinToString(separator = "\n", prefix = "\n") { row ->
        row.joinToString(separator = "") { if (it) "██" else ".." }
      }
  }

  private fun applyOperations(): Array<BooleanArray> {
    val width = 50
    val height = 6
    val screen = Array(height) { BooleanArray(width) }
    lines
      .mapNotNull { line ->
        PATTERN.matchEntire(line)?.let { match ->
          val (command, first, second) = match.destructured
          Triple(command, first.toInt(), second.toInt())
        }
      }
      .forEach { (command, first, second) ->
        when (command) {
          "rect " ->
            (0 until second).forEach { y -> screen[y].fill(true, 0, first) }
          "rotate row y=" -> {
            val row = BooleanArray(width)
            (0 until width).forEach { row[(it + second) % width] = screen[first][it] }
            (0 until width).forEach { screen[first][it] = row[it] }
          }
          "rotate column x=" -> {
            val column = BooleanArray(height)
            (0 until height).forEach { column[(it + second) % height] = screen[it][first] }
            (0 until height).forEach { screen[it][first] = column[it] }
          }
        }
      }

    return screen
  }
}

fun main() = SomeDay.mainify(Day8)
