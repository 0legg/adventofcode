package net.olegg.aoc.year2019.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2019.DayOf2019

/**
 * See [Year 2019, Day 6](https://adventofcode.com/2019/day/6)
 */
object Day6 : DayOf2019(6) {
  override fun first(data: String): Any? {
    val orbits = data
        .trim()
        .lines()
        .map { line -> line.split(")").let { it.first() to it.last() } }
        .toMutableList()

    val available = orbits.map { it.second }.toMutableSet()
    val counts = orbits.flatMap { it.toList() }.map { it to 0 }.toMap().toMutableMap()

    while (available.isNotEmpty()) {
      val curr = available.first { planet -> orbits.none { it.first == planet } }
      val orbit = orbits.first { it.second == curr }
      counts[orbit.first] = counts.getOrDefault(orbit.first, 0) + counts.getOrDefault(orbit.second, 0) + 1
      available.remove(curr)
      orbits.remove(orbit)
    }

    return counts.values.sum()
  }
}

fun main() = SomeDay.mainify(Day6)
