package net.olegg.aoc.year2024.day19

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 19](https://adventofcode.com/2024/day/19)
 */
object Day19 : DayOf2024(19) {
  override fun first(): Any? {
    val (rawPatterns, rawTowels) = data.split("\n\n")

    val patterns = rawPatterns.split(", ")

    return rawTowels.lines().count { towel ->
      val reachable = MutableList(towel.length + 1) { false }
      reachable[0] = true

      reachable.indices.forEach { index ->
        if (reachable[index]) {
          val tail = towel.substring(index)
          patterns.forEach { pattern ->
            if (tail.startsWith(pattern)) {
              reachable[index + pattern.length] = true
            }
          }
        }
      }

      reachable.last()
    }
  }

  override fun second(): Any? {
    val (rawPatterns, rawTowels) = data.split("\n\n")

    val patterns = rawPatterns.split(", ")

    return rawTowels.lines().sumOf { towel ->
      val reachable = MutableList(towel.length + 1) { 0L }
      reachable[0] = 1L

      reachable.indices.forEach { index ->
        val count = reachable[index]
        if (count != 0L) {
          val tail = towel.substring(index)
          patterns.forEach { pattern ->
            if (tail.startsWith(pattern)) {
              reachable[index + pattern.length] += count
            }
          }
        }
      }

      reachable.last()
    }
  }
}

fun main() = SomeDay.mainify(Day19)
