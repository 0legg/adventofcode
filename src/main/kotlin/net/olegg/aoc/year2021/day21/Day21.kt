package net.olegg.aoc.year2021.day21

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 21](https://adventofcode.com/2021/day/21)
 */
object Day21 : DayOf2021(21) {
  override fun first(data: String): Any? {
    val start = data.trim()
      .lines()
      .map { it.split(" ").last().toInt() }

    val dice = generateSequence(1) { if (it == 100) 1 else it + 1 }
    val counter = generateSequence(0) { it + 3 }

    return dice.chunked(3)
      .map { it.sum() }
      .scan(Triple(start.first() to 0, start.last() to 0, 0)) { (first, second, move), value ->
        if (move == 0) {
          val newPos = (first.first - 1 + value) % 10 + 1
          Triple(newPos to first.second + newPos, second, 1)
        } else {
          val newPos = (second.first - 1 + value) % 10 + 1
          Triple(first, newPos to second.second + newPos, 0)
        }
      }
      .map { it.first.second to it.second.second }
      .zip(counter)
      .first { it.first.first >= 1000 || it.first.second >= 1000 }
      .let { minOf(it.first.first, it.first.second) * it.second }
  }
}

fun main() = SomeDay.mainify(Day21)
