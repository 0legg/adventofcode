package net.olegg.aoc.year2015.day8

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 8](https://adventofcode.com/2015/day/8)
 */
object Day8 : DayOf2015(8) {
  private val strings = data.trim().lines()
  override fun first(data: String): Any? {
    return strings.sumBy { it.length } -
      strings.sumBy { line ->
        line
          .replace("^\"".toRegex(), "")
          .replace("\"$".toRegex(), "")
          .replace("\\\"", "\"")
          .replace("\\\\", "\\")
          .replace("\\\\x[0-9a-f]{2}".toRegex(), "#")
          .length
      }
  }

  override fun second(data: String): Any? {
    return strings
      .sumBy { line ->
        line
          .map { char ->
            when (char) {
              '\"' -> "\\\""
              '\\' -> "\\\\"
              else -> "$char"
            }
          }
          .joinToString(prefix = "\"", postfix = "\"", separator = "").length
      } - strings.sumBy { it.length }
  }
}

fun main() = SomeDay.mainify(Day8)
