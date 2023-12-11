package net.olegg.aoc.year2023.day11

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.pairs
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 11](https://adventofcode.com/2023/day/11)
 */
object Day11 : DayOf2023(11) {
  override fun first(): Any? {
    return solve(2)
  }

  override fun second(): Any? {
    return solve(1000000)
  }

  private fun solve(add: Long): Long {
    val emptyRows = matrix.mapIndexedNotNull { index, row ->
      index.takeIf { row.all { it == '.' } }
    }
    val emptyColumns = matrix.first().indices
      .filter { column ->
        matrix.all { it[column] == '.' }
      }

    val galaxies = matrix.flatMapIndexed { y, row ->
      row.mapIndexedNotNull { x, c ->
        if (c == '#') {
          Vector2D(x, y)
        } else {
          null
        }
      }
    }

    return galaxies.pairs()
      .map {
        val dx = (minOf(it.first.x, it.second.x)..<maxOf(it.first.x, it.second.x))
          .sumOf { x ->
            if (x in emptyColumns) add else 1
          }

        val dy = (minOf(it.first.y, it.second.y)..<maxOf(it.first.y, it.second.y))
          .sumOf { y ->
            if (y in emptyRows) add else 1
          }

        dx + dy
      }
      .sum()
  }
}

fun main() = SomeDay.mainify(Day11)
