package net.olegg.aoc.year2017.day19

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.fit
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2017.DayOf2017
import kotlin.math.abs

/**
 * See [Year 2017, Day 19](https://adventofcode.com/2017/day/19)
 */
object Day19 : DayOf2017(19) {
  override fun first(data: String): Any? {
    val map = data
      .lines()
      .map { it.toList() }

    var pos = Vector2D(map[0].indexOfFirst { it == '|' }, 0)
    var dir = Vector2D(0, 1)
    val result = StringBuilder()
    while (map[pos] != null) {
      val char = map[pos]!!
      if (char.isLetter()) {
        result.append(char)
      }
      val dirs = listOf(dir) +
        Neighbors4.map { it.step }.filter { abs(it.x) != abs(dir.x) || abs(it.y) != abs(dir.y) }
      val newDir = dirs.firstOrNull { curr ->
        val next = pos + curr
        map.fit(next) && map[next] != ' '
      }
      if (newDir != null) {
        dir = newDir
        pos = pos + dir
      } else {
        break
      }
    }
    return result
  }

  override fun second(data: String): Any? {
    val map = data
      .lines()
      .map { it.toList() }

    var pos = Vector2D(map[0].indexOfFirst { it == '|' }, 0)
    var dir = Vector2D(0, 1)
    var steps = 0
    while (true) {
      steps += 1
      val dirs = listOf(dir) +
        Neighbors4.map { it.step }.filter { abs(it.x) != abs(dir.x) || abs(it.y) != abs(dir.y) }
      val newDir = dirs.firstOrNull { curr ->
        val next = pos + curr
        map.fit(next) && map[next] != ' '
      }
      if (newDir != null) {
        dir = newDir
        pos = pos + dir
      } else {
        break
      }
    }
    return steps
  }
}

fun main() = SomeDay.mainify(Day19)
