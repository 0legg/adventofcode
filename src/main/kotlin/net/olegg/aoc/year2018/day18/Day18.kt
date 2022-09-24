package net.olegg.aoc.year2018.day18

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.Neighbors8
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 18](https://adventofcode.com/2018/day/18)
 */
object Day18 : DayOf2018(18) {
  override fun first(): Any? {
    val map = data
      .trim()
      .lines()
      .map { it.toList() }

    return solve(map, 10)
  }

  override fun second(): Any? {
    val map = data
      .trim()
      .lines()
      .map { it.toList() }

    return solve(map, 1000000000)
  }

  private fun solve(map: List<List<Char>>, minutes: Int): Int {
    val cache = mutableMapOf(map.joinToString(separator = "") { it.joinToString(separator = "") } to 0)
    val after = (1..minutes).fold(map) { acc, round ->
      val curr = acc.mapIndexed { y, row ->
        row.mapIndexed { x, c ->
          val pos = Vector2D(x, y)
          val adjacent = Neighbors8
            .map { pos + it.step }
            .mapNotNull { acc[it] }
          when (c) {
            '.' -> if (adjacent.count { it == '|' } >= 3) '|' else '.'
            '|' -> if (adjacent.count { it == '#' } >= 3) '#' else '|'
            '#' -> if (adjacent.contains('#') and adjacent.contains('|')) '#' else '.'
            else -> c
          }
        }
      }

      val footprint = curr.joinToString(separator = "") { it.joinToString(separator = "") }
      cache[footprint]?.let { head ->
        val cycle = round - head
        val tail = (minutes - head) % cycle
        val target = head + tail
        val final = cache
          .filterValues { it == target }
          .keys
          .first()

        return final.count { it == '#' } * final.count { it == '|' }
      }

      cache[footprint] = round
      return@fold curr
    }

    return after.sumOf { row -> row.count { it == '#' } } * after.sumOf { row -> row.count { it == '|' } }
  }
}

fun main() = SomeDay.mainify(Day18)
