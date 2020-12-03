package net.olegg.aoc.year2020.day3

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 3](https://adventofcode.com/2020/day/3)
 */
object Day3 : DayOf2020(3) {
  override fun first(data: String): Any? {
    val forest = data
        .trim()
        .lines()

    return forest.mapIndexed { row, s -> s[(row * 3) % s.length] }.count { it == '#' }
  }
}

fun main() = SomeDay.mainify(Day3)
