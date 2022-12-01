package net.olegg.aoc.year2021.day21

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2021.DayOf2021
import java.util.PriorityQueue

/**
 * See [Year 2021, Day 21](https://adventofcode.com/2021/day/21)
 */
object Day21 : DayOf2021(21) {
  override fun first(): Any? {
    val start = lines.map { it.split(" ").last().toInt() }

    val dice = generateSequence(1) { if (it == 100) 1 else it + 1 }
    val counter = generateSequence(0) { it + 3 }

    val startUniverse = Universe(
      listOf(
        State(start.first(), 0),
        State(start.last(), 0),
      ),
      move = 0,
    )

    return dice
      .chunked(3)
      .map { it.sum() }
      .scan(startUniverse) { universe, increase ->
        Universe(
          states = universe.states.mapIndexed { index, value ->
            if (index == universe.move) {
              val newPos = (value.pos - 1 + increase) % 10 + 1
              State(newPos, value.score + newPos)
            } else {
              value
            }
          },
          move = 1 - universe.move
        )
      }
      .map { un -> un.states.map { it.score } }
      .zip(counter)
      .first { (scores, _) -> scores.any { it >= 1000 } }
      .let { (scores, dices) -> scores.minOf { it } * dices }
  }

  override fun second(): Any? {
    val start = lines.map { it.split(" ").last().toInt() }

    val increases = (1..3).flatMap { a ->
      (1..3).flatMap { b ->
        (1..3).map { c ->
          a + b + c
        }
      }
    }
      .groupingBy { it }
      .eachCount()
      .mapValues { it.value.toLong() }

    val startUniverse = Universe(
      listOf(
        State(start.first(), 0),
        State(start.last(), 0),
      ),
      move = 0,
    )
    val knownUniverses = mutableMapOf(startUniverse to 1L)
    val queue = PriorityQueue<Universe>(compareBy { un -> un.states.sumOf { it.score } })
    queue += startUniverse

    while (queue.isNotEmpty()) {
      val curr = queue.remove()
      val currCount = knownUniverses.getOrDefault(curr, 0L)
      val active = curr.states[curr.move]
      val newUniverses = increases
        .map { (increase, count) ->
          val newPos = (active.pos - 1 + increase) % 10 + 1
          State(newPos, active.score + newPos) to count
        }
        .map { (state, count) ->
          Universe(
            states = curr.states.mapIndexed { index, value ->
              if (index == curr.move) state else value
            },
            move = 1 - curr.move
          ) to count
        }

      newUniverses.forEach { (universe, count) ->
        if (universe !in knownUniverses && universe.states.none { it.score >= 21 }) {
          queue += universe
        }
        knownUniverses[universe] = knownUniverses.getOrDefault(universe, 0) + count * currCount
      }
    }

    return knownUniverses.filter { un -> un.key.states.any { it.score >= 21 } }
      .toList()
      .groupBy { 1 - it.first.move }
      .mapValues { un -> un.value.sumOf { it.second } }
      .maxOf { it.value }
  }

  data class Universe(
    val states: List<State>,
    val move: Int,
  )

  data class State(
    val pos: Int,
    val score: Int,
  )
}

fun main() = SomeDay.mainify(Day21)
