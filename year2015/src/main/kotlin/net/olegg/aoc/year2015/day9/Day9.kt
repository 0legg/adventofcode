package net.olegg.aoc.year2015.day9

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.permutations
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 9](https://adventofcode.com/2015/day/9)
 */
object Day9 : DayOf2015(9) {
  private val PATTERN = "^\\b(\\w*)\\b to \\b(\\w*)\\b = (\\d*)$".toRegex()

  private val EDGES = lines
    .flatMap { line ->
      PATTERN.matchEntire(line)?.let { match ->
        val (city1, city2, distance) = match.destructured
        return@let listOf(
          Pair(city1, city2) to distance.toInt(),
          Pair(city2, city1) to distance.toInt(),
        )
      }.orEmpty()
    }
    .toMap()

  private val CITIES = EDGES.keys.flatMap { listOf(it.first, it.second) }.distinct()

  override fun first(): Any? {
    return CITIES.permutations()
      .minOf { city ->
        city
          .zipWithNext()
          .sumOf { EDGES[it] ?: 0 }
      }
  }

  override fun second(): Any? {
    return CITIES.permutations()
      .maxOf { city ->
        city
          .zipWithNext()
          .sumOf { EDGES[it] ?: 0 }
      }
  }
}

fun main() = SomeDay.mainify(Day9)
