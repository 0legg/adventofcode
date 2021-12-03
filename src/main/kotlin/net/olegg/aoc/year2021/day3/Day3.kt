package net.olegg.aoc.year2021.day3

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 3](https://adventofcode.com/2021/day/3)
 */
object Day3 : DayOf2021(3) {
  override fun first(data: String): Any? {
    val values = data.trim().lines()
    val length = values.first().length
    val counts = values
      .map { line -> line.map { ch -> ch.digitToInt() } }
      .fold(List(length) { 0 }) { acc, value ->
        acc.zip(value) { a, b -> a + b }
      }

    val gamma = counts.joinToString(separator = "") { if (it * 2 >= values.size) "1" else "0" }.toInt(2)
    val epsilon = counts.joinToString(separator = "") { if (it * 2 >= values.size) "0" else "1" }.toInt(2)
    return gamma * epsilon
  }
}

fun main() = SomeDay.mainify(Day3)
