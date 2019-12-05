package net.olegg.aoc.year2017.day1

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 1](https://adventofcode.com/2017/day/1)
 */
object Day1 : DayOf2017(1) {
  override fun first(data: String): Any? {
    return data.trim()
        .let { it + it[0] }
        .windowed(2)
        .filter { it[0] == it[1] }
        .map { Character.digit(it[0], 10) }
        .sum()
  }

  override fun second(data: String): Any? {
    val source = data.trim()
    val shifted = data.substring(data.length / 2, data.length) + data.substring(0, data.length / 2)

    return source.zip(shifted)
        .filter { it.first == it.second }
        .map { Character.digit(it.first, 10) }
        .sum()
  }
}

fun main() = SomeDay.mainify(Day1)
