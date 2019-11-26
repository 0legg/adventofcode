package net.olegg.aoc.year2015.day20

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 20](https://adventofcode.com/2015/day/20)
 */
object Day20 : DayOf2015(20) {
  val max = data.toInt()

  override fun first(data: String): Any? {
    return generateSequence(1) { it + 1 }.first { house ->
      (1..Math.sqrt(house.toDouble()).toInt()).sumBy { if (house % it == 0) (it + house / it) * 10 else 0 } >= max
    }
  }

  override fun second(data: String): Any? {
    return generateSequence(1) { it + 1 }.first { house ->
      (1..Math.sqrt(house.toDouble()).toInt()).sumBy {
        if (house % it == 0) 11 * listOf(it, house / it).filter { house <= it * 50 }.sum() else 0
      } >= max
    }
  }
}

fun main() = SomeDay.mainify(Day20)
