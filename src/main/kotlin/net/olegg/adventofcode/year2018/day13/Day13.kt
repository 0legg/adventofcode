package net.olegg.adventofcode.year2018.day13

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018

/**
 * @see <a href="http://adventofcode.com/2018/day/13">Year 2018, Day 13</a>
 */
class Day13 : DayOf2018(13) {
  companion object {
    private val MOVES = listOf(
        (-1 to 0),
        (0 to 1),
        (1 to 0),
        (0 to -1)
    )
  }

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
        .mapIndexed { y, line ->
          line.mapIndexedNotNull { x, c ->
            when (c) {
              '^' -> Triple((x to y), (0 to -1), 0)
              'v' -> Triple((x to y), (0 to 1), 0)
              '<' -> Triple((x to y), (-1 to 0), 0)
              '>' -> Triple((x to y), (1 to 0), 0)
              else -> null
            }
          }
        }
        .flatten()
        .sortedWith(compareBy({ it.first.second }, { it.first.first }))

    (0..1_000_000).fold(trains) { state, _ ->
      val coords = state.map { it.first }
      val newState = state
          .mapIndexed { index, train ->
            val (x, y) = train.first
            val (vx, vy) = train.second
            val turn = train.third
            val (nvx, nvy, nt) = when (tracks[y][x]) {
              '\\' -> Triple(vy, vx, turn)
              '/' -> Triple(-vy, -vx, turn)
              '+' -> when (turn % 3) {
                0 -> {
                  val (tx, ty) = MOVES[(MOVES.indexOf(vx to vy) + 1) % 4]
                  Triple(tx, ty, turn + 1)
                }
                1 -> Triple(vx, vy, turn + 1)
                2 -> {
                  val (tx, ty) = MOVES[(MOVES.indexOf(vx to vy) + 3) % 4]
                  Triple(tx, ty, turn + 1)
                }
                else -> Triple(vx, vy, turn)
              }
              else -> Triple(vx, vy, turn)
            }
            val newTrain = (x + nvx to y + nvy)

            if (newTrain in coords.subList(index + 1, coords.size)) {
              return "${newTrain.first},${newTrain.second}"
            }

            Triple(newTrain, (nvx to nvy), nt)
          }

      val newCoords = newState.map { it.first }
      newCoords.forEachIndexed { index, newTrain ->
        if (newTrain in newCoords.subList(0, index)) {
          return "${newTrain.first},${newTrain.second}"
        }
      }

      newState.sortedWith(compareBy({ it.first.second }, { it.first.first }))
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
        .mapIndexed { y, line ->
          line.mapIndexedNotNull { x, c ->
            when (c) {
              '^' -> Triple((x to y), (0 to -1), 0)
              'v' -> Triple((x to y), (0 to 1), 0)
              '<' -> Triple((x to y), (-1 to 0), 0)
              '>' -> Triple((x to y), (1 to 0), 0)
              else -> null
            }
          }
        }
        .flatten()
        .sortedWith(compareBy({ it.first.second }, { it.first.first }))

    (0..1_000_000).fold(trains) { state, _ ->
      val newState = mutableListOf<Triple<Pair<Int, Int>, Pair<Int, Int>, Int>>()
      val removed = mutableSetOf<Int>()
      state
          .forEachIndexed { index, train ->
            if (index !in removed) {
              val (x, y) = train.first
              val (vx, vy) = train.second
              val turn = train.third
              val (nvx, nvy, nt) = when (tracks[y][x]) {
                '\\' -> Triple(vy, vx, turn)
                '/' -> Triple(-vy, -vx, turn)
                '+' -> when (turn % 3) {
                  0 -> {
                    val (tx, ty) = MOVES[(MOVES.indexOf(vx to vy) + 1) % 4]
                    Triple(tx, ty, turn + 1)
                  }
                  1 -> Triple(vx, vy, turn + 1)
                  2 -> {
                    val (tx, ty) = MOVES[(MOVES.indexOf(vx to vy) + 3) % 4]
                    Triple(tx, ty, turn + 1)
                  }
                  else -> Triple(vx, vy, turn)
                }
                else -> Triple(vx, vy, turn)
              }
              val newTrain = (x + nvx to y + nvy)

              if (newTrain in state.map { it.first }.subList(index + 1, state.size)) {
                removed += state.indexOfFirst { it.first == newTrain }
              } else if (newTrain in newState.map { it.first }) {
                newState.removeIf { it.first == newTrain }
              } else {
                newState.add(Triple(newTrain, (nvx to nvy), nt))
              }
            }
          }

      if (newState.size == 1) {
        val newTrain = newState.first().first
        return "${newTrain.first},${newTrain.second}"
      }

      newState.sortedWith(compareBy({ it.first.second }, { it.first.first }))
    }

    return null
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day13::class)
