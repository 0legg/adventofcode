package net.olegg.aoc.year2021.day3

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 3](https://adventofcode.com/2021/day/3)
 */
object Day3 : DayOf2021(3) {
  override fun first(): Any? {
    val length = lines.first().length
    val counts = lines
      .map { line -> line.map { ch -> ch.digitToInt() } }
      .fold(List(length) { 0 }) { acc, value ->
        acc.zip(value) { a, b -> a + b }
      }

    val gamma = counts.joinToString(separator = "") { if (it * 2 >= lines.size) "1" else "0" }.toInt(2)
    val epsilon = counts.joinToString(separator = "") { if (it * 2 >= lines.size) "0" else "1" }.toInt(2)
    return gamma * epsilon
  }

  override fun second(): Any? {
    val length = lines.first().length

    val oxygen = (0..<length)
      .fold(lines) { acc, position ->
        if (acc.size == 1) return@fold acc
        val sum = acc.sumOf { it[position].digitToInt() }
        val char = if (sum * 2 >= acc.size) '1' else '0'
        return@fold acc.filter { it[position] == char }
      }
      .first()
      .toInt(2)

    val co2 = (0..<length)
      .fold(lines) { acc, position ->
        if (acc.size == 1) return@fold acc
        val sum = acc.sumOf { it[position].digitToInt() }
        val char = if (sum * 2 < acc.size) '1' else '0'
        return@fold acc.filter { it[position] == char }
      }
      .first()
      .toInt(2)

    return oxygen * co2
  }
}

fun main() = SomeDay.mainify(Day3)
