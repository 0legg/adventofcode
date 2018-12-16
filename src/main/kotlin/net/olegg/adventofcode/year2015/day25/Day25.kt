package net.olegg.adventofcode.year2015.day25

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * @see <a href="http://adventofcode.com/2015/day/25">Year 2015, Day 25</a>
 */
class Day25 : DayOf2015(25) {
  companion object {
    val PATTERN = ".*\\b(\\d+)\\b.*\\b(\\d+)\\b".toRegex()
  }

  override fun first(data: String): Any? {
    return PATTERN
        .find(data)
        ?.let { match ->
          val (row, column) = match.destructured.toList().map { it.toInt() }
          val pos = (row + column - 1) * (row + column - 2) / 2 + column
          return@let (1 until pos).fold(20151125L) { acc, _ -> (acc * 252533L) % 33554393L }
        }
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day25::class)
