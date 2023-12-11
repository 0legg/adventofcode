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
    val emptyRows = matrix.mapIndexedNotNull { index, row ->
      index.takeIf { row.all { it == '.' } }
    }
    val emptyColumns = matrix.first().indices
      .filter { column ->
        matrix.all { it[column] == '.' }
      }

    val sparseMatrix = matrix.flatMapIndexed { y, row ->
      val mapped = row.flatMapIndexed { x, char ->
        if (x in emptyColumns) {
          listOf(char, char)
        } else {
          listOf(char)
        }
      }
      if (y in emptyRows) {
        listOf(mapped, mapped)
      } else {
        listOf(mapped)
      }
    }

    val galaxies = sparseMatrix.flatMapIndexed { y, row ->
      row.mapIndexedNotNull { x, c ->
        if (c == '#') {
          Vector2D(x, y)
        } else {
          null
        }
      }
    }

    return galaxies.pairs()
      .sumOf { (it.second - it.first).manhattan() }
  }
}

fun main() = SomeDay.mainify(Day11)
