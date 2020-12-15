package net.olegg.aoc.year2019.day24

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
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

  override fun second(data: String): Any? {
    val start = data
      .trim()
      .lines()
      .map { it.toList() }

    val size = start.size
    val center = Vector2D(size / 2, size / 2)
    val empty = List(size * size) { '.' }.chunked(size)

    val result = (1..200).fold(mapOf(0 to start).withDefault { empty }) { acc, count ->
      (-count..count).associateWith { level ->
        val prev = acc.getValue(level)

        return@associateWith prev.mapIndexed { y, row ->
          row.mapIndexed { x, c ->
            val base = Vector2D(x, y)
            val neighbors = Neighbors4.map { it to it.step + base }
              .flatMap { (dir, point) ->
                when {
                  point == center -> when (dir) {
                    L -> (0 until size).map { level - 1 to Vector2D(size - 1, it) }
                    R -> (0 until size).map { level - 1 to Vector2D(0, it) }
                    U -> (0 until size).map { level - 1 to Vector2D(it, size - 1) }
                    D -> (0 until size).map { level - 1 to Vector2D(it, 0) }
                    else -> emptyList()
                  }
                  point.x < 0 -> listOf(level + 1 to center + L.step)
                  point.x >= size -> listOf(level + 1 to center + R.step)
                  point.y < 0 -> listOf(level + 1 to center + U.step)
                  point.y >= size -> listOf(level + 1 to center + D.step)
                  else -> listOf(level to point)
                }
              }
              .count { (level, point) ->
                acc.getValue(level)[point] == '#'
              }
            when {
              base == center -> '?'
              c == '.' && neighbors in 1..2 -> '#'
              c == '#' && neighbors != 1 -> '.'
              else -> c
            }
          }
        }
      }.withDefault { empty }
    }

    return result.values
      .sumBy { level ->
        level.sumBy { row ->
          row.count { it == '#' }
        }
      }
  }

  private fun List<List<Char>>.footprint() = joinToString("") { it.joinToString("") }

  private operator fun <T> List<List<T>>.get(v: Vector2D): T? = when {
    v.y !in indices -> null
    v.x !in this[v.y].indices -> null
    else -> this[v.y][v.x]
  }
}

fun main() = SomeDay.mainify(Day24)
