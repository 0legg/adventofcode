package net.olegg.aoc.year2015.day3

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 3](https://adventofcode.com/2015/day/3)
 */
object Day3 : DayOf2015(3) {
  private val MAPPING = mapOf(
    '<' to L,
    '>' to R,
    '^' to U,
    'v' to D,
  )
  private val MOVES = data.map { MAPPING[it]?.step ?: Vector2D() }

  override fun first(): Any? {
    return visit(MOVES).size
  }

  override fun second(): Any? {
    val (even, odd) = MOVES
      .withIndex()
      .partition { it.index % 2 == 0 }
    return (visit(even.map { it.value }) + visit(odd.map { it.value })).size
  }

  private fun visit(moves: List<Vector2D>): Set<Vector2D> {
    return moves.scan(Vector2D()) { pos, move -> pos + move }.toSet()
  }
}

fun main() = SomeDay.mainify(Day3)
