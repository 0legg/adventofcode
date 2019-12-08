package net.olegg.aoc.year2019.day8

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2019.DayOf2019

/**
 * See [Year 2019, Day 8](https://adventofcode.com/2019/day/8)
 */
object Day8 : DayOf2019(8) {
  override fun first(data: String): Any? {
    val layers = data
        .trim()
        .chunked(25 * 6)

    return layers.minBy { layer -> layer.count { it == '0'} }
        .orEmpty()
        .let { layer -> layer.count { it == '1'} * layer.count { it == '2'} }
  }
}

fun main() = SomeDay.mainify(Day8)
