package net.olegg.aoc.year2018.day13

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.CCW
import net.olegg.aoc.utils.CW
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 13](https://adventofcode.com/2018/day/13)
 */
object Day13 : DayOf2018(13) {
  private val TURNS_SLASH = mapOf(
    U to R,
    R to U,
    D to L,
    L to D
  )

  private val TURNS_BACKSLASH = mapOf(
    U to L,
    L to U,
    D to R,
    R to D
  )

  override fun first(data: String): Any? {
    val input = data
      .lines()
      .filterNot { it.isEmpty() }
      .map { it.toList() }

    val tracks = input
      .map { line ->
        line.map { char ->
          when (char) {
            '^', 'v' -> '|'
            '<', '>' -> '-'
            else -> char
          }
        }
      }

    val trains = input
      .flatMapIndexed { y, line ->
        line.mapIndexedNotNull { x, c ->
          val pos = Vector2D(x, y)
          when (c) {
            '^' -> Triple(pos, U, 0)
            'v' -> Triple(pos, D, 0)
            '<' -> Triple(pos, L, 0)
            '>' -> Triple(pos, R, 0)
            else -> null
          }
        }
      }
      .sortedWith(compareBy({ it.first.y }, { it.first.x }))

    (0..1_000_000).fold(trains) { state, _ ->
      val coords = state.map { it.first }.toMutableSet()
      val newState = state
        .map { (pos, dir, turn) ->
          val (newDir, newTurn) = when (tracks[pos]) {
            '\\' -> TURNS_BACKSLASH[dir] to turn
            '/' -> TURNS_SLASH[dir] to turn
            '+' -> when (turn % 3) {
              0 -> CCW[dir] to turn + 1
              1 -> dir to turn + 1
              2 -> CW[dir] to turn + 1
              else -> dir to turn
            }
            else -> dir to turn
          }
          val newPos = pos + newDir!!.step
          coords -= pos
          if (newPos in coords) {
            return "${newPos.x},${newPos.y}"
          }
          coords += newPos

          Triple(newPos, newDir, newTurn)
        }

      return@fold newState.sortedWith(compareBy({ it.first.y }, { it.first.x }))
    }

    return null
  }

  override fun second(data: String): Any? {
    val input = data
      .lines()
      .filterNot { it.isEmpty() }
      .map { it.toList() }

    val tracks = input
      .map { line ->
        line.map { char ->
          when (char) {
            '^', 'v' -> '|'
            '<', '>' -> '-'
            else -> char
          }
        }
      }

    val trains = input
      .flatMapIndexed { y, line ->
        line.mapIndexedNotNull { x, c ->
          val pos = Vector2D(x, y)
          when (c) {
            '^' -> Triple(pos, U, 0)
            'v' -> Triple(pos, D, 0)
            '<' -> Triple(pos, L, 0)
            '>' -> Triple(pos, R, 0)
            else -> null
          }
        }
      }
      .sortedWith(compareBy({ it.first.y }, { it.first.x }))

    (0..1_000_000).fold(trains) { state, _ ->
      val newState = mutableListOf<Triple<Vector2D, Directions, Int>>()
      val removed = mutableSetOf<Int>()

      state
        .forEachIndexed { index, (pos, dir, turn) ->
          if (index !in removed) {
            val (newDir, newTurn) = when (tracks[pos]) {
              '\\' -> TURNS_BACKSLASH[dir] to turn
              '/' -> TURNS_SLASH[dir] to turn
              '+' -> when (turn % 3) {
                0 -> CCW[dir] to turn + 1
                1 -> dir to turn + 1
                2 -> CW[dir] to turn + 1
                else -> dir to turn
              }
              else -> dir to turn
            }

            when (val newPos = pos + newDir!!.step) {
              in state.map { it.first }.subList(index + 1, state.size) -> {
                removed += state.indexOfFirst { it.first == newPos }
              }
              in newState.map { it.first } -> {
                newState.removeIf { it.first == newPos }
              }
              else -> {
                newState.add(Triple(newPos, newDir, newTurn))
              }
            }
          }
        }

      if (newState.size == 1) {
        val newTrain = newState.first().first
        return "${newTrain.x},${newTrain.y}"
      }

      return@fold newState.sortedWith(compareBy({ it.first.y }, { it.first.x }))
    }

    return null
  }
}

fun main() = SomeDay.mainify(Day13)
