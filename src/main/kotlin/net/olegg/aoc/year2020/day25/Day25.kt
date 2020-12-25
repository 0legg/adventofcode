package net.olegg.aoc.year2020.day25

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 25](https://adventofcode.com/2020/day/25)
 */
object Day25 : DayOf2020(25) {
  private val modulo = 20201227L
  
  override fun first(data: String): Any? {
    val items = data
      .trim()
      .parseLongs(delimiters = "\n")

    val loop = generateSequence(1L) { (it * 7L) % modulo }
      .indexOfFirst { it == items[0] }

    return (0 until loop).fold(1L) { acc, _ -> (acc * items[1]) % modulo }
  }
}

fun main() = SomeDay.mainify(Day25)
