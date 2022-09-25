package net.olegg.aoc.year2015.day15

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 15](https://adventofcode.com/2015/day/15)
 */
object Day15 : DayOf2015(15) {
  private const val spoons = 100
  private val PATTERN = ".* (-?\\d+)\\b.* (-?\\d+)\\b.* (-?\\d+)\\b.* (-?\\d+)\\b.* (-?\\d+)\\b.*".toRegex()

  private val items = lines
    .map { line ->
      PATTERN.matchEntire(line)
        ?.destructured
        ?.toList()
        ?.map { it.toInt() }
        .orEmpty()
    }

  override fun first(): Any? {
    val itemsValues = (0..3).map { value -> items.map { it[value] } }
    return splitRange(items.size, spoons)
      .map { split ->
        itemsValues.map { item ->
          item.mapIndexed { index, value -> split[index] * value }.sum().coerceAtLeast(0)
        }
      }
      .maxOfOrNull { it.reduce { acc, value -> acc * value } }
  }

  override fun second(): Any? {
    val itemsValues = (0..3).map { value -> items.map { it[value] } }
    val calories = items.map { it[4] }
    return splitRange(items.size, spoons)
      .filter { it.mapIndexed { index, value -> calories[index] * value }.sum() == 500 }
      .map { split ->
        itemsValues.map { item ->
          item.mapIndexed { index, value -> split[index] * value }.sum().coerceAtLeast(0)
        }
      }
      .maxOfOrNull { it.reduce { acc, value -> acc * value } }
  }
}

fun splitRange(splits: Int, sum: Int): List<List<Int>> = if (splits == 1) listOf(listOf(sum)) else {
  (0..sum).flatMap { value -> splitRange(splits - 1, sum - value).map { listOf(value) + it } }
}

fun main() = SomeDay.mainify(Day15)
