package net.olegg.adventofcode.year2018.day18

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018

/**
 * @see <a href="http://adventofcode.com/2018/day/18">Year 2018, Day 18</a>
 */
class Day18 : DayOf2018(18) {
  companion object {
    private val MOVE = listOf(
        -1 to -1,
        -1 to 0,
        -1 to 1,
        0 to -1,
        0 to 1,
        1 to -1,
        1 to 0,
        1 to 1
    )
  }

  override fun first(data: String): Any? {
    val map = data
        .trim()
        .lines()
        .map { it.toList() }

    val after = (1..10).fold(map) { acc, _ ->
      acc.mapIndexed { y, row ->
        row.mapIndexed { x, c ->
          val adjacent = MOVE
              .map { it.first + x to it.second + y }
              .filter { it.second in acc.indices }
              .filter { it.first in row.indices }
              .map { acc[it.second][it.first] }
          when (c) {
            '.' -> if (adjacent.count { it == '|' } >= 3) '|' else '.'
            '|' -> if (adjacent.count { it == '#' } >= 3) '#' else '|'
            '#' -> if (adjacent.contains('#') and adjacent.contains('|')) '#' else '.'
            else -> c
          }
        }
      }
    }

    return after.sumBy { row -> row.count { it == '#' } } * after.sumBy { row -> row.count { it == '|' } }
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day18::class)
