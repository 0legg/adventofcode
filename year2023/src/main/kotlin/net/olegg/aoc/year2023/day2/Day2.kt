package net.olegg.aoc.year2023.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 2](https://adventofcode.com/2023/day/2)
 */
object Day2 : DayOf2023(2) {
  private val GAME_PATTERN = "Game \\d+: ".toRegex()
  override fun first(): Any? {
    return lines
      .asSequence()
      .map { line -> line.replace(GAME_PATTERN, "") }
      .map { line -> line.split("; ") }
      .map { series ->
        series
          .map { cubes ->
            cubes.split(", ")
              .map { it.split(" ").toPair() }
              .associate { it.second to it.first.toInt() }
          }
          .fold(emptyMap<String, Int>()) { acc, value ->
            (acc.toList() + value.toList())
              .groupBy { it.first }
              .mapValues { colors -> colors.value.maxOf { it.second } }
          }
      }
      .withIndex()
      .filter { (_, map) ->
        map.getOrDefault("red", 0) <= 12 && map.getOrDefault("green", 0) <= 13 && map.getOrDefault("blue", 0) <= 14
      }
      .sumOf { it.index + 1 }
  }
}

fun main() = SomeDay.mainify(Day2)
