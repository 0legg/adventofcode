package net.olegg.aoc.year2017.day19

import kotlin.math.abs
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 19](https://adventofcode.com/2017/day/19)
 */
object Day19 : DayOf2017(19) {
  override fun first(data: String): Any? {
    val vector = listOf(
        -1 to 0,
        1 to 0,
        0 to -1,
        0 to 1
    )
    val map = data
        .lines()
        .map { it.toList() }

    var pos = map[0].indexOfFirst { it == '|' } to 0
    var dir = 0 to 1
    val result = StringBuilder()
    while (fit(map, pos)) {
      val char = map[pos.second][pos.first]
      if (char.isLetter()) {
        result.append(char)
      }
      val dirs = listOf(dir) +
          vector.filter { abs(it.first) != abs(dir.first) || abs(it.second) != abs(dir.second) }
      val newDir = dirs.firstOrNull { curr ->
        val next = pos.first + curr.first to pos.second + curr.second
        fit(map, next) && map[next.second][next.first] != ' '
      }
      if (newDir != null) {
        dir = newDir
        pos = pos.first + dir.first to pos.second + dir.second
      } else {
        pos = -1 to -1
      }
    }
    return result
  }

  override fun second(data: String): Any? {
    val vector = listOf(
        -1 to 0,
        1 to 0,
        0 to -1,
        0 to 1
    )
    val map = data
        .lines()
        .map { it.toList() }

    var pos = map[0].indexOfFirst { it == '|' } to 0
    var dir = 0 to 1
    var steps = 0
    while (fit(map, pos)) {
      steps += 1
      val dirs = listOf(dir) +
          vector.filter { abs(it.first) != abs(dir.first) || abs(it.second) != abs(dir.second) }
      val newDir = dirs.firstOrNull { curr ->
        val next = pos.first + curr.first to pos.second + curr.second
        fit(map, next) && map[next.second][next.first] != ' '
      }
      if (newDir != null) {
        dir = newDir
        pos = pos.first + dir.first to pos.second + dir.second
      } else {
        pos = -1 to -1
      }
    }
    return steps
  }

  private fun fit(map: List<List<Any>>, pos: Pair<Int, Int>) =
      pos.second in map.indices && pos.first in map[pos.second].indices
}

fun main() = SomeDay.mainify(Day19)
