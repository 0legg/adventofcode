package net.olegg.aoc.year2016.day24

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.permutations
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 24](https://adventofcode.com/2016/day/24)
 */
object Day24 : DayOf2016(24) {
  override fun first(): Any? {
    val map = matrix

    val locations = ('0'..'7').flatMap { index ->
      map.mapIndexedNotNull { row, chars ->
        Vector2D(chars.indexOf(index), row).takeIf { it.x != -1 }
      }
    }

    val distances = (0..7).map { dist ->
      val start = locations[dist]
      val visit = mutableMapOf(start to 0)
      val queue = ArrayDeque(listOf(start))

      while (queue.isNotEmpty()) {
        val pos = queue.removeFirst()
        val steps = visit[pos] ?: 0
        Neighbors4
          .map { pos + it.step }
          .filterNot { it in visit }
          .filterNot { map[it.y][it.x] == '#' }
          .also { cells ->
            cells.forEach { cell -> visit[cell] = steps + 1 }
            queue += cells
          }
      }

      return@map locations.map { visit[it] }
    }

    return (1..7)
      .toList()
      .permutations()
      .map { listOf(0) + it }
      .minOf { route ->
        route.windowed(2).fold(0) { acc, points ->
          acc + (distances[points[0]][points[1]] ?: 0)
        }
      }
  }

  override fun second(): Any? {
    val map = matrix

    val locations = ('0'..'7').flatMap { index ->
      map.mapIndexedNotNull { row, chars ->
        Vector2D(chars.indexOf(index), row).takeIf { it.x != -1 }
      }
    }

    val distances = (0..7).map { dist ->
      val start = locations[dist]
      val visit = mutableMapOf(start to 0)
      val queue = ArrayDeque(listOf(start))

      while (queue.isNotEmpty()) {
        val pos = queue.removeFirst()
        val steps = visit[pos] ?: 0
        Neighbors4
          .map { pos + it.step }
          .filterNot { it in visit }
          .filterNot { map[it.y][it.x] == '#' }
          .also { cells ->
            cells.forEach { cell -> visit[cell] = steps + 1 }
            queue.addAll(cells)
          }
      }

      return@map locations.map { visit[it] }
    }

    return (1..7)
      .toList()
      .permutations()
      .map { listOf(0) + it + listOf(0) }
      .minOf { route ->
        route.windowed(2).fold(0) { acc, points ->
          acc + (distances[points[0]][points[1]] ?: 0)
        }
      }
  }
}

fun main() = SomeDay.mainify(Day24)
