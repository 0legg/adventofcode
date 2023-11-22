package net.olegg.aoc.year2020.day11

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.NEXT_8
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 11](https://adventofcode.com/2020/day/11)
 */
object Day11 : DayOf2020(11) {
  override fun first(): Any? {
    val map = matrix

    val steps = generateSequence(map) { curr ->
      curr.mapIndexed { y, row ->
        row.mapIndexed { x, c ->
          val place = Vector2D(x, y)
          when {
            c == 'L' && NEXT_8.map { it.step + place }.none { curr[it] == '#' } -> '#'
            c == '#' && NEXT_8.map { it.step + place }.count { curr[it] == '#' } >= 4 -> 'L'
            else -> c
          }
        }
      }
    }.zipWithNext()

    return steps
      .first { it.first == it.second }
      .first
      .sumOf { line -> line.count { it == '#' } }
  }

  override fun second(): Any? {
    val map = matrix

    val steps = generateSequence(map) { curr ->
      curr.mapIndexed { y, row ->
        row.mapIndexed { x, c ->
          val place = Vector2D(x, y)
          val occupied = NEXT_8
            .map { it.step }
            .map { step ->
              generateSequence(1) { it + 1 }
                .map { place + step * it }
                .first { curr[it] != '.' }
                .let { curr[it] }
            }
            .count { it == '#' }
          when {
            c == 'L' && occupied == 0 -> '#'
            c == '#' && occupied >= 5 -> 'L'
            else -> c
          }
        }
      }
    }.zipWithNext()

    return steps
      .first { it.first == it.second }
      .first
      .sumOf { line -> line.count { it == '#' } }
  }
}

fun main() = SomeDay.mainify(Day11)
