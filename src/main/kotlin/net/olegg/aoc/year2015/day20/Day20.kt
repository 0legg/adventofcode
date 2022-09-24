package net.olegg.aoc.year2015.day20

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015
import kotlin.math.sqrt

/**
 * See [Year 2015, Day 20](https://adventofcode.com/2015/day/20)
 */
object Day20 : DayOf2015(20) {
  private val max = data.trim().toInt()

  override fun first(): Any? {
    return generateSequence(1) { it + 1 }.first { house ->
      (1..sqrt(house.toDouble()).toInt()).sumOf {
        if (house % it == 0) (it + house / it) * 10 else 0
      } >= max
    }
  }

  override fun second(): Any? {
    return generateSequence(1) { it + 1 }.first { house ->
      (1..sqrt(house.toDouble()).toInt()).sumOf { elf ->
        if (house % elf == 0) 11 * listOf(elf, house / elf).filter { house <= it * 50 }.sum() else 0
      } >= max
    }
  }
}

fun main() = SomeDay.mainify(Day20)
