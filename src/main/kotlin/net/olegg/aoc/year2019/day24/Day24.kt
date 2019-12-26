package net.olegg.aoc.year2019.day24

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2019.DayOf2019

/**
 * See [Year 2019, Day 24](https://adventofcode.com/2019/day/24)
 */
object Day24 : DayOf2019(24) {
  override fun first(data: String): Any? {
    val start = data
        .trim()
        .lines()
        .map { it.toList() }

    val visited = mutableSetOf<String>()
    var curr = start
    while (curr.footprint() !in visited) {
      visited += curr.footprint()

      curr = curr.mapIndexed { y, row ->
        row.mapIndexed { x, c ->
          val neighbors = Neighbors4.map { it.step + Vector2D(x, y) }
              .mapNotNull { curr[it] }
              .count { it == '#' }
          when {
            c == '.' && neighbors in 1..2 -> '#'
            c == '#' && neighbors != 1 -> '.'
            else -> c
          }
        }
      }
    }

    return curr.footprint()
        .reversed()
        .replace('.', '0')
        .replace('#', '1')
        .toBigInteger(2)
  }

  private fun List<List<Char>>.footprint() = joinToString("") { it.joinToString("") }

  private operator fun <T> List<List<T>>.get(v: Vector2D): T? = when {
    v.y !in indices -> null
    v.x !in this[v.y].indices -> null
    else -> this[v.y][v.x]
  }
}

fun main() = SomeDay.mainify(Day24)
