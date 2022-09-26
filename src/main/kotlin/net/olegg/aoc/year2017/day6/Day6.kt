package net.olegg.aoc.year2017.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 6](https://adventofcode.com/2017/day/6)
 */
object Day6 : DayOf2017(6) {
  override fun first(): Any? {
    val blocks = data.parseInts("\t")

    val seen = mutableSetOf<List<Int>>()
    var curr = blocks
    var steps = 0
    while (curr !in seen) {
      seen.add(curr)
      steps += 1
      val max = curr.max()
      val position = curr.indexOfFirst { it == max }
      val add = max / curr.size
      val tail = max % curr.size

      curr = curr.mapIndexed { index, value ->
        return@mapIndexed add +
          (if (index == position) 0 else value) +
          (if (index in (position + 1)..(position + tail)) 1 else 0) +
          (if ((position + tail >= curr.size) && (index in 0..(position + tail) % curr.size)) 1 else 0)
      }
    }
    return steps
  }

  override fun second(): Any? {
    val blocks = data.parseInts("\t")

    val seen = mutableMapOf<List<Int>, Int>()
    var curr = blocks
    var steps = 0
    while (curr !in seen) {
      seen[curr] = steps
      steps += 1
      val max = curr.max()
      val position = curr.indexOfFirst { it == max }
      val add = max / curr.size
      val tail = max % curr.size

      curr = curr.mapIndexed { index, value ->
        return@mapIndexed add +
          (if (index == position) 0 else value) +
          (if (index in (position + 1)..(position + tail)) 1 else 0) +
          (if ((position + tail >= curr.size) && (index in 0..(position + tail) % curr.size)) 1 else 0)
      }
    }
    return steps - (seen[curr] ?: 0)
  }
}

fun main() = SomeDay.mainify(Day6)
