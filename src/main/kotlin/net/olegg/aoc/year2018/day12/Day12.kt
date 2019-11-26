package net.olegg.aoc.year2018.day12

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 12](https://adventofcode.com/2018/day/12)
 */
class Day12 : DayOf2018(12) {
  override fun first(data: String): Any? {
    return solve(data, 20)
  }

  override fun second(data: String): Any? {
    return solve(data, 50_000_000_000L)
  }

  private fun solve(data: String, gens: Long): Long {
    val initialStateRaw = data
        .lines()[0]
        .substringAfter(": ")

    val initialShift = initialStateRaw.indexOf('#').toLong()
    val initialState = initialStateRaw.trim('.') to (0L to initialShift)

    val padding = "...."

    val states = mutableMapOf(initialState)

    val rules = data
        .trim()
        .lines()
        .drop(2)
        .map { line ->
          line.split(" => ".toRegex()).let { it.first() to it.last() }
        }
        .toMap()

    val finalState = (1..gens).fold(initialState) { state, gen ->
      val tempState = padding + state.first + padding
      val newStateRaw = tempState
          .windowed(size = 5)
          .map { rules.getOrDefault(it, '.') }
          .joinToString(separator = "")
      val shift = newStateRaw.indexOf('#') + state.second.second - 2
      val newState = newStateRaw.trim('.') to (gen to shift)
      if (newState.first in states) {
        val (oldGen, oldShift) = states[newState.first] ?: (0L to 0L)
        val cycle = gen - oldGen
        val cycleShift = shift - oldShift
        val aggregateShift = shift + cycleShift * ((gens - gen) / cycle)
        val tail = (gens - gen) % cycle
        val finalValue = states.entries
            .find { it.value.first == oldGen + tail }
            ?.toPair()
            ?: "" to (0L to 0L)

        val final = finalValue.first to (gens to aggregateShift + finalValue.second.second - oldShift)
        return final
            .first
            .mapIndexed { index, c -> if (c == '#') index + final.second.second else 0L }
            .sum()
      }
      states += newState
      newState
    }

    return finalState
        .first
        .mapIndexed { index, c -> if (c == '#') index + finalState.second.second else 0L }
        .sum()
  }
}

fun main() = SomeDay.mainify(Day12::class)
