package net.olegg.aoc.year2021.day7

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2021.DayOf2021
import kotlin.math.abs

/**
 * See [Year 2021, Day 7](https://adventofcode.com/2021/day/7)
 */
object Day7 : DayOf2021(7) {
  override fun first(): Any? {
    val positions = data.parseInts(",")

    val min = positions.min()
    val max = positions.max()

    return (min..max).minOf { pos ->
      positions.sumOf { abs(it - pos) }
    }
  }

  override fun second(): Any? {
    val positions = data.parseInts(",")

    val min = positions.minOf { it }
    val max = positions.maxOf { it }

    return (min..max).minOf { pos ->
      positions.sumOf {
        val dist = abs(it - pos)
        dist * (dist + 1) / 2
      }
    }
  }
}

fun main() = SomeDay.mainify(Day7)
