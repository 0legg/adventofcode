package net.olegg.aoc.year2020.day11

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Neighbors8
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 11](https://adventofcode.com/2020/day/11)
 */
object Day11 : DayOf2020(11) {
  override fun first(data: String): Any? {
    val map = data
      .trim()
      .lines()
      .map { it.toList() }

    val steps = generateSequence(map) { curr ->
      curr.mapIndexed { y, row ->
        row.mapIndexed { x, c ->
          val place = Vector2D(x, y)
          when {
            c == 'L' && Neighbors8.map { it.step + place }.none { curr[it] == '#' } -> '#'
            c == '#' && Neighbors8.map { it.step + place }.count { curr[it] == '#' } >= 4 -> 'L'
            else -> c
          }
        }
      }
    }.windowed(2)

    return steps
      .first { it.first() == it.last() }
      .first()
      .sumBy { line -> line.count { it == '#' } }
  }
}

fun main() = SomeDay.mainify(Day11)
