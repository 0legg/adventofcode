package net.olegg.aoc.year2022.day14

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.DL
import net.olegg.aoc.utils.Directions.DR
import net.olegg.aoc.utils.Directions.UL
import net.olegg.aoc.utils.Directions.UR
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.set
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 14](https://adventofcode.com/2022/day/14)
 */
object Day14 : DayOf2022(14) {
  private val STOP = setOf('#', null)

  override fun first(): Any? {
    val map = fill(false)

    return map.sumOf { row ->
      row.count {
        it == 'o'
      }
    }
  }

  override fun second(): Any? {
    val map = fill(true)

    return map.sumOf { row ->
      row.count {
        it == 'o'
      }
    }
  }

  private fun fill(addFloor: Boolean): List<List<Char>> {
    val start = Vector2D(500, 0)
    val rocks = lines
      .flatMap { line ->
        line.split(" -> ")
          .map { it.parseInts(",") }
          .map { Vector2D(it.first(), it.last()) }
          .zipWithNext()
          .flatMap { (from, to) ->
            val vector = (to - from).dir()
            generateSequence(from) { it + vector }
              .takeWhile { it != to } + to
          }
      }
      .toSet()

    val minX = minOf(start.x - 1, rocks.minOf { it.x - 1 })
    val maxX = maxOf(start.x + 1, rocks.maxOf { it.x + 1 })
    val minY = minOf(start.y, rocks.minOf { it.y })
    val maxY = maxOf(start.y, rocks.maxOf { it.y })
    val addY = if (addFloor) 3 else 0
    val extendX = if (addFloor) maxY + addY - minY + 1 else 0
    val bbox = Pair(
      Vector2D(minX - extendX, minY),
      Vector2D(maxX + extendX, maxY + addY),
    )

    val map = List(bbox.second.y - bbox.first.y + 1) {
      MutableList(bbox.second.x - bbox.first.x + 1) { '.' }
    }

    map[start - bbox.first] = '+'

    rocks.forEach { rock ->
      map[rock - bbox.first] = '#'
    }

    if (addFloor) {
      map[map.lastIndex - 1].fill('#')
    }

    fill(map, start - bbox.first)

    return map
  }

  private fun fill(map: List<MutableList<Char>>, coord: Vector2D): Boolean {
    val reachBottom = when {
      map[coord] in STOP -> false
      coord.y == map.lastIndex -> true
      map[coord] == '~' -> true
      map[coord] == 'o' -> false
      else -> {
        map[coord] = 'o'
        fill(map, coord + D.step) || fill(map, coord + DL.step) || fill(map, coord + DR.step)
      }
    }

    if (reachBottom) {
      map[coord] = '~'
    }

    return reachBottom
  }
}

fun main() = SomeDay.mainify(Day14)
