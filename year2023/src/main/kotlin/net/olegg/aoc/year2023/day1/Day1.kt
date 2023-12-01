package net.olegg.aoc.year2023.day1

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.findAllOverlapping
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 1](https://adventofcode.com/2023/day/1)
 */
object Day1 : DayOf2023(1) {
  private val NUMBERS = listOf(
    "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
  )
  private val PATTERN = NUMBERS.joinToString(prefix = "[1-9]|", separator = "|").toRegex()

  override fun first(): Any? {
    return lines
      .map { line ->
        line.filter { it.isDigit() }
      }
      .sumOf { "${it.first()}${it.last()}".toInt() }
  }

  override fun second(): Any? {
    return lines
      .map { line ->
        PATTERN.findAllOverlapping(line).toList()
      }
      .map { matches ->
        matches.first().value to matches.last().value
      }
      .sumOf { "${it.first.wordToInt()}${it.second.wordToInt()}".toInt() }
  }

  private fun String.wordToInt() = toIntOrNull() ?: NUMBERS.indexOf(this)
}

fun main() = SomeDay.mainify(Day1)
