package net.olegg.aoc.year2018.day17

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.set
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 17](https://adventofcode.com/2018/day/17)
 */
object Day17 : DayOf2018(17) {
  private val PATTERN = "(\\w)=(\\d+), \\w=(\\d+)..(\\d+)".toRegex()
  private val WATER = setOf('~', '|')
  private val STOP = setOf('~', '#', null)

  override fun first(): Any? {
    val (xs, ys, map) = fill()

    return ys.sumOf { y ->
      xs.count { x ->
        map[y][x] in WATER
      }
    }
  }

  override fun second(): Any? {
    val (xs, ys, map) = fill()

    return ys.sumOf { y ->
      xs.count { x ->
        map[y][x] == '~'
      }
    }
  }

  private fun fill(): Triple<IntRange, IntRange, List<MutableList<Char>>> {
    val start = Vector2D(500, 0)
    val clay = lines
      .mapNotNull { line ->
        PATTERN.matchEntire(line)?.let { match ->
          val (direction, valueRaw, rangeFromRaw, rangeToRaw) = match.destructured
          return@mapNotNull if (direction == "x") {
            valueRaw.toInt()..valueRaw.toInt() to rangeFromRaw.toInt()..rangeToRaw.toInt()
          } else {
            rangeFromRaw.toInt()..rangeToRaw.toInt() to valueRaw.toInt()..valueRaw.toInt()
          }
        }
      }

    val minX = minOf(start.x - 1, clay.minOf { it.first.first - 1 })
    val maxX = maxOf(start.x + 1, clay.maxOf { it.first.last + 1 })
    val minY = minOf(start.y, clay.minOf { it.second.first })
    val maxY = maxOf(start.y, clay.maxOf { it.second.last })
    val bbox = Pair(
      Vector2D(minX, minY),
      Vector2D(maxX, maxY),
    )

    val map = List(maxY - minY + 1) {
      MutableList(maxX - minX + 1) { '.' }
    }

    clay.forEach { line ->
      line.second.forEach { yRaw ->
        line.first.forEach { xRaw ->
          map[Vector2D(xRaw, yRaw) - bbox.first] = '#'
        }
      }
    }

    fill(map, start - bbox.first)

    return Triple(
      IntRange(0, maxX - minX),
      IntRange(
        clay.minOf { it.second.first } - minY,
        clay.maxOf { it.second.last } - minY,
      ),
      map,
    )
  }

  private fun fill(map: List<MutableList<Char>>, coord: Vector2D): Boolean {
    val reachBottom = when {
      map[coord] in STOP -> false
      coord.y == map.lastIndex -> true
      map[coord] == '|' -> true
      else -> {
        map[coord] = '~'
        fill(map, coord + D.step) || (fill(map, coord + L.step) or fill(map, coord + R.step))
      }
    }

    if (reachBottom) {
      map[coord] = '|'

      generateSequence(coord) { it + L.step }
        .drop(1)
        .takeWhile { map[it] == '~' }
        .forEach { map[it] = '|' }
      generateSequence(coord) { it + R.step }
        .drop(1)
        .takeWhile { map[it] == '~' }
        .forEach { map[it] = '|' }
    }

    return reachBottom
  }
}

fun main() = SomeDay.mainify(Day17)
