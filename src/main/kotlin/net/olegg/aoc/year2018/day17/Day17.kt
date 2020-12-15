package net.olegg.aoc.year2018.day17

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018
import kotlin.math.max
import kotlin.math.min

/**
 * See [Year 2018, Day 17](https://adventofcode.com/2018/day/17)
 */
object Day17 : DayOf2018(17) {
  private val PATTERN = "(\\w)=(\\d+), (\\w)=(\\d+)..(\\d+)".toRegex()
  private val WATER = listOf('~', '|')

  override fun first(data: String): Any? {
    val (xs, ys, map) = fill(data)

    return ys.sumBy { y ->
      xs.count { x ->
        map[y][x] in WATER
      }
    }
  }

  override fun second(data: String): Any? {
    val (xs, ys, map) = fill(data)

    return ys.sumBy { y ->
      xs.count { x ->
        map[y][x] == '~'
      }
    }
  }

  private fun fill(data: String): Triple<IntRange, IntRange, List<MutableList<Char>>> {
    val start = 500 to 0
    val clay = data
      .trim()
      .lines()
      .mapNotNull { line ->
        PATTERN.matchEntire(line)?.let { match ->
          val (direction, valueRaw, _, rangeFromRaw, rangeToRaw) = match.destructured
          return@mapNotNull if (direction == "x") {
            valueRaw.toInt()..valueRaw.toInt() to rangeFromRaw.toInt()..rangeToRaw.toInt()
          } else {
            rangeFromRaw.toInt()..rangeToRaw.toInt() to valueRaw.toInt()..valueRaw.toInt()
          }
        }
      }

    val bbox = clay
      .fold(start.first - 1..start.first + 1 to start.second..start.second) { acc, value ->
        val (accxRange, accyRange) = acc
        val (xRange, yRange) = value
        return@fold Pair(
          min(accxRange.first, xRange.first - 1)..max(accxRange.last, xRange.last + 1),
          min(accyRange.first, yRange.first)..max(accyRange.last, yRange.last)
        )
      }

    val map = List(bbox.second.last - bbox.second.first + 1) {
      MutableList(bbox.first.last - bbox.first.first + 1) { '.' }
    }

    clay.forEach { line ->
      line.second.forEach { yRaw ->
        line.first.forEach { xRaw ->
          map[yRaw - bbox.second.first][xRaw - bbox.first.first] = '#'
        }
      }
    }

    fill(map, start.first - bbox.first.first to start.second - bbox.second.first)

    return Triple(
      IntRange(0, bbox.first.last - bbox.first.first),
      IntRange(
        (clay.map { it.second.first }.minOrNull() ?: bbox.second.first) - bbox.second.first,
        (clay.map { it.second.last }.maxOrNull() ?: bbox.second.last) - bbox.second.first
      ),
      map
    )
  }

  private fun fill(map: List<MutableList<Char>>, coord: Pair<Int, Int>): Boolean {
    val reachBottom = when {
      coord.second !in map.indices -> false
      coord.first !in map[coord.second].indices -> false
      coord.second == map.lastIndex -> {
        map[coord.second][coord.first] = '|'
        true
      }
      map[coord.second][coord.first] == '|' -> {
        true
      }
      map[coord.second][coord.first] in setOf('~', '#') -> {
        false
      }
      else -> {
        map[coord.second][coord.first] = '~'
        if (map[coord.second + 1][coord.first] == '|' ||
          (map[coord.second + 1][coord.first] == '.' && fill(map, coord.first to coord.second + 1))
        ) {
          true
        } else {
          val left = fill(map, coord.first - 1 to coord.second)
          val right = fill(map, coord.first + 1 to coord.second)
          left || right
        }
      }
    }

    if (reachBottom) {
      map[coord.second][coord.first] = '|'
      for (x in coord.first - 1 downTo 0) {
        if (map[coord.second][x] == '~') {
          map[coord.second][x] = '|'
        } else {
          break
        }
      }
      for (x in coord.first + 1..map[coord.second].indices.last) {
        if (map[coord.second][x] == '~') {
          map[coord.second][x] = '|'
        } else {
          break
        }
      }
    }

    return reachBottom
  }
}

fun main() = SomeDay.mainify(Day17)
