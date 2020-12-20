package net.olegg.aoc.year2020.day20

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 20](https://adventofcode.com/2020/day/20)
 */
object Day20 : DayOf2020(20) {
  private val NUMBER = "\\d+".toRegex()
  override fun first(data: String): Any? {
    val tiles = data
      .trim()
      .split("\n\n")
      .map { it.lines() }
      .map { (NUMBER.find(it.first())?.value?.toIntOrNull() ?: 0) to it.drop(1).map { l -> l.toList() } }
      .map { it.first to profiles(it.second) }

    val profileCounts = tiles.flatMap { it.second }
      .groupBy { it }
      .mapValues { it.value.size }

    val corners = tiles.filter { it.second.count { profileCounts[it] == 1 } == 4 }

    return corners.map { it.first.toLong() }.reduce(Long::times)
  }

  private fun profiles(tile: List<List<Char>>): List<Int> {
    val top = tile.first()
    val bottom = tile.last()
    val left = tile.map { it.first() }
    val right = tile.map { it.last() }

    return listOf(top, right, bottom, left)
      .flatMap { listOf(it, it.reversed()) }
      .map { it.joinToString(separator = "") { when(it) {
        '#' -> "1"
        '.' -> "0"
        else -> "0"
      } } }
      .map { it.toInt(2) }
  }
}

fun main() = SomeDay.mainify(Day20)
