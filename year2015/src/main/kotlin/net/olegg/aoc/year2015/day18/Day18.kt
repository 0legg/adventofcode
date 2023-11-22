package net.olegg.aoc.year2015.day18

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.NEXT_8
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.fit
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 18](https://adventofcode.com/2015/day/18)
 */
object Day18 : DayOf2015(18) {
  private val SIZE = matrix.size

  private val FIELD = matrix
    .flatMapIndexed { y, row ->
      row.mapIndexedNotNull { x, char ->
        if (char == '#') Vector2D(x, y) else null
      }
    }
    .toSet()

  override fun first(): Any? {
    return (1..SIZE).fold(FIELD) { field, _ -> move(field) }.size
  }

  override fun second(): Any? {
    val corners = setOf(
      Vector2D(0, 0),
      Vector2D(0, SIZE - 1),
      Vector2D(SIZE - 1, 0),
      Vector2D(SIZE - 1, SIZE - 1),
    )
    return (1..SIZE).fold(FIELD + corners) { field, _ -> move(field) + corners }.size
  }

  private fun move(field: Set<Vector2D>): Set<Vector2D> {
    val neighbors = field
      .flatMap { cell -> NEXT_8.map { cell + it.step } }
      .filter { matrix.fit(it) }
      .groupingBy { it }
      .eachCount()
      .toList()
      .partition { it.first in field }

    return (neighbors.first.filter { it.second in 2..3 } + neighbors.second.filter { it.second == 3 })
      .map { it.first }
      .toSet()
  }
}

fun main() = SomeDay.mainify(Day18)
