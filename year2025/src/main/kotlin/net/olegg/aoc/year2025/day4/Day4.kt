package net.olegg.aoc.year2025.day4

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2025.DayOf2025

/**
 * See [Year 2025, Day 4](https://adventofcode.com/2025/day/4)
 */
object Day4 : DayOf2025(4) {
  override fun first(): Any? {
    return matrix.mapIndexed { y, row ->
      row.mapIndexed { x, char ->
        val pos = Vector2D(x, y)
        when {
          char != '@' -> 0
          Directions.NEXT_8.count { matrix[it.step + pos] == '@' } < 4 -> 1
          else -> 0
        }
      }.sum()
    }.sum()
  }

  override fun second(): Any? {
    val finalMatrix = generateSequence(matrix) { currMatrix ->
      currMatrix.mapIndexed { y, row ->
        row.mapIndexed { x, char ->
          val pos = Vector2D(x, y)
          when {
            char != '@' -> char
            Directions.NEXT_8.count { currMatrix[it.step + pos] == '@' } < 4 -> '.'
            else -> char
          }
        }
      }
    }
      .zipWithNext()
      .first { it.first == it.second }
      .first

    return finalMatrix.mapIndexed { y, row ->
      row.mapIndexed { x, char ->
        val pos = Vector2D(x, y)
        if (matrix[pos] != char) 1 else 0
      }.sum()
    }.sum()
  }
}

fun main() = SomeDay.mainify(Day4)
