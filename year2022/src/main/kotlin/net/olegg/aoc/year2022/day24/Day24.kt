package net.olegg.aoc.year2022.day24

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Directions.Companion.NEXT_4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 24](https://adventofcode.com/2022/day/24)
 */
object Day24 : DayOf2022(24) {
  private val STOP = setOf(null, '#')

  override fun first(): Any? {
    val from = Vector2D(
      matrix.first().indexOf('.'),
      0,
    )
    val to = Vector2D(
      matrix.last().indexOf('.'),
      matrix.lastIndex,
    )

    val height = matrix.size
    val width = matrix.first().size

    var blizzards = matrix.flatMapIndexed { y, row ->
      row.mapIndexedNotNull { x, c ->
        val position = Vector2D(x, y)
        when (c) {
          '<' -> position to Directions.L
          '>' -> position to Directions.R
          '^' -> position to Directions.U
          'v' -> position to Directions.D
          else -> null
        }
      }
    }

    var positions = setOf(from)
    var step = 0
    while (to !in positions) {
      step++
      blizzards = blizzards.map { (vector, dir) ->
        val maybeNext = vector + dir.step
        when {
          maybeNext.x == 0 -> Vector2D(width - 2, maybeNext.y)
          maybeNext.x == width - 1 -> Vector2D(1, maybeNext.y)
          maybeNext.y == 0 -> Vector2D(maybeNext.x, height - 2)
          maybeNext.y == height - 1 -> Vector2D(maybeNext.x, 1)
          else -> maybeNext
        } to dir
      }

      positions = positions.flatMap { curr ->
        (listOf(curr) + NEXT_4.map { curr + it.step })
          .filter { matrix[it] !in STOP }
          .filter { next -> blizzards.none { it.first == next } }
      }.toSet()
    }

    return step
  }

  override fun second(): Any? {
    val from = Vector2D(
      matrix.first().indexOf('.'),
      0,
    )
    val to = Vector2D(
      matrix.last().indexOf('.'),
      matrix.lastIndex,
    )

    val height = matrix.size
    val width = matrix.first().size

    var blizzards = matrix.flatMapIndexed { y, row ->
      row.mapIndexedNotNull { x, c ->
        val position = Vector2D(x, y)
        when (c) {
          '<' -> position to Directions.L
          '>' -> position to Directions.R
          '^' -> position to Directions.U
          'v' -> position to Directions.D
          else -> null
        }
      }
    }

    val start = from to 0
    val finish = to to 3
    var positions = setOf(start)
    var step = 0
    while (finish !in positions) {
      step++
      blizzards = blizzards.map { (vector, dir) ->
        val maybeNext = vector + dir.step
        when {
          maybeNext.x == 0 -> Vector2D(width - 2, maybeNext.y)
          maybeNext.x == width - 1 -> Vector2D(1, maybeNext.y)
          maybeNext.y == 0 -> Vector2D(maybeNext.x, height - 2)
          maybeNext.y == height - 1 -> Vector2D(maybeNext.x, 1)
          else -> maybeNext
        } to dir
      }

      positions = positions.flatMap { curr ->
        (listOf(curr.first) + NEXT_4.map { curr.first + it.step })
          .filter { matrix[it] !in STOP }
          .filter { next -> blizzards.none { it.first == next } }
          .map { next ->
            when {
              next == to && curr.second == 0 -> next to 1
              next == from && curr.second == 1 -> next to 2
              next == to && curr.second == 2 -> next to 3
              else -> next to curr.second
            }
          }
      }.toSet()
    }

    return step
  }
}

fun main() = SomeDay.mainify(Day24)
