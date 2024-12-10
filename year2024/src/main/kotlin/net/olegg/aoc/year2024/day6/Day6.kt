package net.olegg.aoc.year2024.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
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

  override fun second(): Any? {
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

    visited -= start

    return visited.count { obstacle ->
      val newMatrix = matrix.mapIndexed { y, row ->
        row.mapIndexed { x, c ->
          if (obstacle.x == x && obstacle.y == y) '#' else c
        }
      }

      var newCurr = start
      var newDir = U
      val seen = mutableSetOf<Pair<Vector2D, Directions>>()

      while (newMatrix[newCurr] != null && (newCurr to newDir) !in seen) {
        seen += newCurr to newDir
        if (newMatrix[newCurr + newDir.step] != '#') {
          newCurr = newCurr + newDir.step
        } else {
          newDir = CW.getValue(newDir)
        }
      }

      newMatrix[newCurr] != null
    }
  }
}

fun main() = SomeDay.mainify(Day6)
