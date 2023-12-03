package net.olegg.aoc.year2023.day3

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.NEXT_8
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 3](https://adventofcode.com/2023/day/3)
 */
object Day3 : DayOf2023(3) {
  private val NOT_SYMBOLS = buildSet<Char?> {
    addAll('0'..'9')
    add('.')
    add(null)
  }

  private val NUMBER = "\\d+".toRegex()

  override fun first(): Any? {
    return lines
      .mapIndexed { y, row ->
        NUMBER.findAll(row)
          .map { match ->
            if (match.range.any { x -> NEXT_8.any { matrix[it.step + Vector2D(x, y)] !in NOT_SYMBOLS } }) {
              match.value.toInt()
            } else {
              0
            }
          }
          .sum()
      }
      .sum()
  }

  override fun second(): Any? {
    val nextToGears = lines
      .flatMapIndexed { y, row ->
        NUMBER.findAll(row)
          .flatMap { match ->
            val value = match.value.toLong()
            match.range
              .flatMap { x ->
                NEXT_8
                  .map { it.step + Vector2D(x, y) }
                  .filter { matrix[it] == '*' }
              }
              .toSet()
              .map { it to value }
          }
      }

    return nextToGears.groupBy { it.first }
      .filterValues { it.size == 2 }
      .mapValues { it.value.first().second * it.value.last().second }
      .toList()
      .sumOf { it.second }
  }
}

fun main() = SomeDay.mainify(Day3)
