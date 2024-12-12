package net.olegg.aoc.year2024.day12

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.find
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.set
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 12](https://adventofcode.com/2024/day/12)
 */
object Day12 : DayOf2024(12) {
  override fun first(): Any? {
    val regions = matrix.map { row ->
      MutableList(row.size) { Int.MAX_VALUE }
    }

    var start = regions.find(Int.MAX_VALUE)
    val groups = mutableListOf<Set<Vector2D>>()
    while (start != null) {
      val char = matrix[start]
      val visited = mutableSetOf<Vector2D>()
      val queue = ArrayDeque(listOf(start))

      while (queue.isNotEmpty()) {
        val curr = queue.removeFirst()
        if (curr in visited) {
          continue
        }
        visited += curr
        regions[curr] = groups.size

        queue += Directions.NEXT_4
          .map { curr + it.step }
          .filter { matrix[it] == char }
          .filter { it !in visited }
      }

      groups += visited
      start = regions.find(Int.MAX_VALUE)
    }

    return groups.sumOf { group ->
      group.size * group.sumOf { cell ->
        Directions.NEXT_4.count { matrix[cell] != matrix[cell + it.step] }
      }
    }
  }
}

fun main() = SomeDay.mainify(Day12)
