package net.olegg.aoc.year2017.day6

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 6](https://adventofcode.com/2017/day/6)
 */
object Day6 : DayOf2017(6) {
  override fun first(): Any? {
    val blocks = data.parseInts()

    val seen = mutableSetOf<List<Int>>()
    var curr = blocks
    var steps = 0
    while (curr !in seen) {
      seen.add(curr)
      steps += 1
      val max = curr.maxOrNull() ?: 0
      val position = curr.indexOfFirst { it == max }

      curr = curr.mapIndexed { index, value ->
        val add = max / curr.size
        val bonus = max % curr.size

        return@mapIndexed add +
          (if (index == position) 0 else value) +
          (if (index in (position + 1)..(position + bonus)) 1 else 0) +
          (if ((position + bonus >= curr.size) && (index in 0..(position + bonus) % curr.size)) 1 else 0)
      }
    }
    return steps
  }

  override fun second(): Any? {
    val blocks = data.parseInts()

    val seen = mutableMapOf<List<Int>, Int>()
    var curr = blocks
    var steps = 0
    while (curr !in seen) {
      seen[curr] = steps
      steps += 1
      val max = curr.maxOrNull() ?: 0
      val position = curr.indexOfFirst { it == max }

      curr = curr.mapIndexed { index, value ->
        val add = max / curr.size
        val bonus = max % curr.size

        return@mapIndexed add +
          (if (index == position) 0 else value) +
          (if (index in (position + 1)..(position + bonus)) 1 else 0) +
          (if ((position + bonus >= curr.size) && (index in 0..(position + bonus) % curr.size)) 1 else 0)
      }
    }
    return steps - (seen[curr] ?: 0)
  }
}

fun main() = SomeDay.mainify(Day6)
