package net.olegg.aoc.year2016.day15

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 15](https://adventofcode.com/2016/day/15)
 */
object Day15 : DayOf2016(15) {
  private val PATTERN = "Disc #(\\d+) has (\\d+) positions; at time=(\\d+), it is at position (\\d+).".toRegex()

  override fun first(): Any? {
    val discs = lines
      .mapNotNull { line ->
        PATTERN.find(line)?.groupValues?.let { Triple(it[1].toInt(), it[2].toInt(), it[4].toInt()) }
      }

    return solve(discs)
  }

  override fun second(): Any? {
    val discs = lines
      .mapNotNull { line ->
        PATTERN.find(line)?.groupValues?.let { Triple(it[1].toInt(), it[2].toInt(), it[4].toInt()) }
      }

    return solve(discs + listOf(Triple(discs.size + 1, 11, 0)))
  }

  private fun solve(discs: List<Triple<Int, Int, Int>>): Int {
    return generateSequence(0) { it + 1 }
      .first { time ->
        discs.all { (it.third + it.first + time) % it.second == 0 }
      }
  }
}

fun main() = SomeDay.mainify(Day15)
