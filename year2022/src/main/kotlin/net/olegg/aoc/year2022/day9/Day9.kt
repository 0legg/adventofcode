package net.olegg.aoc.year2022.day9

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Directions.Companion.Neighbors8
import net.olegg.aoc.utils.Directions.DL
import net.olegg.aoc.utils.Directions.DR
import net.olegg.aoc.utils.Directions.UL
import net.olegg.aoc.utils.Directions.UR
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2022.DayOf2022
import kotlin.math.absoluteValue

/**
 * See [Year 2022, Day 9](https://adventofcode.com/2022/day/9)
 */
object Day9 : DayOf2022(9) {
  private val diagonals = listOf(UL, UR, DL, DR)
  override fun first(): Any? {
    return solve(2)
  }

  override fun second(): Any? {
    return solve(10)
  }

  private fun solve(size: Int): Int {
    val moves = lines
      .map { it.split(" ").toPair() }
      .asSequence()
      .flatMap { (direction, step) ->
        List(step.toInt()) { Directions.valueOf(direction) }
      }

    val visited = mutableSetOf(Vector2D())

    moves.fold(List(size) { Vector2D() }) { oldRope, move ->
      val newRope = oldRope.drop(1).runningFold(oldRope.first() + move.step) { head, curr ->
        val dist = head - curr
        when {
          dist.x.absoluteValue <= 1 && dist.y.absoluteValue <= 1 -> curr
          dist.manhattan() == 2 -> curr + dist / 2
          else -> diagonals.map { curr + it.step }.minBy { (head - it).manhattan() }
        }
      }

      visited += newRope.last()
      newRope
    }

    return visited.size
  }
}

fun main() = SomeDay.mainify(Day9)
