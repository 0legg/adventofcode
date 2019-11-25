package net.olegg.adventofcode.year2018.day22

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018

/**
 * See [Year 2018, Day 22](https://adventofcode.com/2018/day/22)
 */
class Day22 : DayOf2018(22) {
  override fun first(data: String): Any? {
    val (depthLine, targetLine) = data.trim().lines().map { it.substringAfter(": ") }
    val depth = depthLine.toInt()
    val (tx, ty) = targetLine.split(",").let { it.first().toInt() to it.last().toInt() }

    val erosion = List(tx + 1) { MutableList(ty + 1) { 0L } }

    (0..tx).forEach { x ->
      (0..ty).forEach { y ->
        erosion[x][y] = (when {
          x == 0 && y == 0 -> 0L
          x == tx && y == ty -> 0L
          y == 0 -> x * 16807L
          x == 0 -> y * 48271L
          else -> (erosion[x - 1][y] * erosion[x][y - 1])
        } + depth) % 20183L
      }
    }

    return erosion.map {
      row -> row.map { it % 3L }.sum()
    }.sum()
  }
}

fun main() = SomeDay.mainify(Day22::class)
