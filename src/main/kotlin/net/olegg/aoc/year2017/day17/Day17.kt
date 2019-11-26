package net.olegg.aoc.year2017.day17

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 17](https://adventofcode.com/2017/day/17)
 */
class Day17 : DayOf2017(17) {
  override fun first(data: String): Any? {
    val cycle = mutableListOf(0)
    val step = data.trimIndent().toInt()

    val position = (1..2017).fold(0) { acc, value ->
      val insert = (acc + step) % cycle.size
      cycle.add(insert, value)
      return@fold insert + 1
    }

    return cycle[position % cycle.size]
  }

  override fun second(data: String): Any? {
    val step = data.trimIndent().toInt()
    var next = -1

    (1..50_000_000).fold(0) { acc, value ->
      val insert = (acc + step) % value
      if (insert == 0) {
        next = value
      }
      return@fold insert + 1
    }

    return next
  }
}

fun main() = SomeDay.mainify(Day17::class)
