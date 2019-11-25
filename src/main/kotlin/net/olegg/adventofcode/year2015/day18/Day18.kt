package net.olegg.adventofcode.year2015.day18

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * See [Year 2015, Day 18](https://adventofcode.com/2015/day/18)
 */
class Day18 : DayOf2015(18) {
  val size = 100
  val field = data
      .lines()
      .mapIndexed { row, string ->
        string.mapIndexed { column, char -> if (char == '#') Pair(row, column) else null }
      }
      .flatten()
      .filterNotNull()
      .toSet()

  fun move(field: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
    val neighbors = field
        .flatMap { cell ->
          (-1..1).flatMap { row ->
            (-1..1).map { column ->
              Pair(cell.first + row, cell.second + column)
            }
          }.filterNot { it == cell }
        }
        .filter { it.first >= 0 }
        .filter { it.first < size }
        .filter { it.second >= 0 }
        .filter { it.second < size }
        .groupBy { it }
        .mapValues { it.value.size }
        .toList()
        .partition { field.contains(it.first) }

    return (neighbors.first.filter { it.second in 2..3 } + neighbors.second.filter { it.second == 3 })
        .map { it.first }
        .toSet()
  }

  override fun first(data: String): Any? {
    return (1..size).fold(field) { field, _ -> move(field) }.size
  }

  override fun second(data: String): Any? {
    val corners = setOf(Pair(0, 0), Pair(0, size - 1), Pair(size - 1, 0), Pair(size - 1, size - 1))
    return (1..size).fold(field + corners) { field, _ -> move(field) + corners }.size
  }
}

fun main() = SomeDay.mainify(Day18::class)
