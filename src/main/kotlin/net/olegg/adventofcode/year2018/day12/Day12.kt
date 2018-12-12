package net.olegg.adventofcode.year2018.day12

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018

/**
 * @see <a href="http://adventofcode.com/2018/day/12">Year 2018, Day 12</a>
 */
class Day12 : DayOf2018(12) {
  override fun first(data: String): Any? {
    val initialState = data
        .lines()[0]
        .substringAfter(": ")
        .mapIndexed { index, c -> index to c }
        .filter { it.second == '#' }
        .toMap()

    val rules = data
        .trim()
        .lines()
        .drop(2)
        .map { line ->
          val (from, to) = line.split(" => ".toRegex())
          return@map from to to[0]
        }
        .toMap()

    val finalState = (1..20)
        .fold(initialState) { state, _ ->
          val from = (state.keys.min() ?: 0) - 4
          val to = (state.keys.max() ?: 0) + 4

          return@fold (from..to)
              .map { index ->
                val around = (index - 2..index + 2).map { state.getOrDefault(it, '.') }.joinToString(separator = "")
                return@map index to rules.getOrDefault(around, '.')
              }
              .filter { it.second == '#' }
              .toMap()
        }

    return finalState
        .keys
        .sum()
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day12::class)
