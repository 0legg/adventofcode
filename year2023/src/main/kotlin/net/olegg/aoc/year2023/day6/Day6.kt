package net.olegg.aoc.year2023.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 6](https://adventofcode.com/2023/day/6)
 */
object Day6 : DayOf2023(6) {
  override fun first(): Any? {
    val times = lines.first().parseLongs(" ")
    val dists = lines.last().parseLongs(" ")

    return times.zip(dists) { time, dist ->
      (0..time).count { it * (time - it) >= dist }.toLong()
    }.reduce(Long::times)
  }
}

fun main() = SomeDay.mainify(Day6)
