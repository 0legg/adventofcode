package net.olegg.aoc.year2024.day1

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.transpose
import net.olegg.aoc.year2024.DayOf2024
import kotlin.math.absoluteValue

/**
 * See [Year 2024, Day 1](https://adventofcode.com/2024/day/1)
 */
object Day1 : DayOf2024(1) {
  override fun first(): Any? {
    val (first, second) = lines
      .map { it.parseInts(" ") }
      .transpose()
      .map { it.sorted() }

    return first.zip(second) { a, b -> (a - b).absoluteValue }.sum()
  }

  override fun second(): Any? {
    val (nums, appears) = lines
      .map { it.parseInts(" ") }
      .transpose()

    val freqs = appears.groupingBy { it }.eachCount()

    return nums.sumOf { it * freqs.getOrDefault(it, 0) }
  }
}

fun main() = SomeDay.mainify(Day1)
