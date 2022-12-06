package net.olegg.aoc.year2016.day5

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.md5
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 5](https://adventofcode.com/2016/day/5)
 */
object Day5 : DayOf2016(5) {
  override fun first(): Any? {
    return generateSequence(0) { it + 1 }
      .map { "$data$it".md5() }
      .filter { it.startsWith("00000") }
      .take(8)
      .map { it[5] }
      .joinToString(separator = "")
  }

  override fun second(): Any? {
    val map = mutableMapOf<Char, Char>()
    return generateSequence(0) { it + 1 }
      .map { "$data$it".md5() }
      .filter { it.startsWith("00000") }
      .filter { it[5] in '0'..'7' }
      .map { it[5] to it[6] }
      .filter { it.first !in map }
      .onEach { map[it.first] = it.second }
      .takeWhile { map.size < 8 }
      .toList()
      .sortedBy { it.first }
      .map { it.second }
      .joinToString(separator = "")
  }
}

fun main() = SomeDay.mainify(Day5)
