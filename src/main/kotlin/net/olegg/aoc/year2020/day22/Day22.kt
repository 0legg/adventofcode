package net.olegg.aoc.year2020.day22

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 22](https://adventofcode.com/2020/day/22)
 */
object Day22 : DayOf2020(22) {
  override fun first(data: String): Any? {
    val (p1, p2) = data
      .trim()
      .split("\n\n")
      .map { it.lines().drop(1) }
      .map { ArrayDeque(it.map { card -> card.toInt() }) }
      .toPair()

    while (p1.isNotEmpty() && p2.isNotEmpty()) {
      val v1 = p1.removeFirst()
      val v2 = p2.removeFirst()

      if (v1 > v2) {
        p1 += v1
        p1 += v2
      } else {
        p2 += v2
        p2 += v1
      }
    }

    val result = p1 + p2

    return result.mapIndexed { index, value -> (result.size - index) * value }.sum()
  }
}

fun main() = SomeDay.mainify(Day22)
