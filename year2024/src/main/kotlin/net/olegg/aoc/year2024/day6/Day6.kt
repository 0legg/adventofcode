package net.olegg.aoc.year2024.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.CW
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.find
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 6](https://adventofcode.com/2024/day/6)
 */
object Day6 : DayOf2024(6) {
  override fun first(): Any? {
    val start = matrix.find('^') ?: return null

    var curr = start
    var dir = U
    val visited = mutableSetOf<Vector2D>()
    while (matrix[curr] != null) {
      visited += curr
      if (matrix[curr + dir.step] != '#') {
        curr = curr + dir.step
      } else {
        dir = CW.getValue(dir)
      }
    }

    return visited.size
  }
}

fun main() = SomeDay.mainify(Day6)
