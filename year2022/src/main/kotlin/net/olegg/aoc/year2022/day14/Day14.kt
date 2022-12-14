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
    val map = fill()

    return map.sumOf { row ->
      row.count {
        it == 'o'
      }
    }
  }

  private fun fill(): List<List<Char>> {
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
    val bbox = Pair(
      Vector2D(minX, minY),
      Vector2D(maxX, maxY),
    )

    val map = List(maxY - minY + 1) {
      MutableList(maxX - minX + 1) { '.' }
    }

    rocks.forEach { rock ->
      map[rock - bbox.first] = '#'
    }

    fill(map, start - bbox.first)

    return map
  }

  private fun fill(map: List<MutableList<Char>>, coord: Vector2D): Boolean {
    val reachBottom = when {
      map[coord] in STOP -> false
      coord.y == map.lastIndex -> true
      map[coord] == '~' -> true
      else -> {
        map[coord] = 'o'
        fill(map, coord + D.step) || fill(map, coord + DL.step) || fill(map, coord + DR.step)
      }
    }

    if (reachBottom) {
      map[coord] = '~'

      generateSequence(coord) { it + UL.step }
        .drop(1)
        .takeWhile { map[it] == 'o' }
        .forEach { map[it] = '~' }
      generateSequence(coord) { it + UR.step }
        .drop(1)
        .takeWhile { map[it] == '0' }
        .forEach { map[it] = '~' }
    }

    return reachBottom
  }
}

fun main() = SomeDay.mainify(Day14)
