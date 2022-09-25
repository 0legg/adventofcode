package net.olegg.aoc.year2020.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 2](https://adventofcode.com/2020/day/2)
 */
object Day2 : DayOf2020(2) {
  private val PATTERN = "(\\d*)-(\\d*) (.): (.*)".toRegex()

  override fun first(): Any? {
    val values = lines
      .mapNotNull { PATTERN.find(it) }
      .map { it.groupValues.drop(1) }

    return values.count { (low, high, char, string) ->
      val lowInt = low.toInt()
      val highInt = high.toInt()
      val ch = char[0]
      return@count string.count { it == ch } in (lowInt..highInt)
    }
  }

  override fun second(): Any? {
    val values = lines
      .mapNotNull { PATTERN.find(it) }
      .map { it.groupValues.drop(1) }

    return values.count { (low, high, char, string) ->
      val lowInt = low.toInt() - 1
      val highInt = high.toInt() - 1
      val ch = char[0]
      return@count (string[lowInt] == ch) xor (string[highInt] == ch)
    }
  }
}

fun main() = SomeDay.mainify(Day2)
