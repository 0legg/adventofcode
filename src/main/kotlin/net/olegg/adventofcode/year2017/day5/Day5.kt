package net.olegg.adventofcode.year2017.day5

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.utils.scan
import net.olegg.adventofcode.year2017.DayOf2017

/**
 * See [Year 2017, Day 5](https://adventofcode.com/2017/day/5)
 */
class Day5 : DayOf2017(5) {
  override fun first(data: String): Any? {
    val values = data.lines()
        .map { it.toInt() }
        .toTypedArray()

    return generateSequence(0) { it + 1 }.scan(Triple(values, 0, 0)) { acc, _ ->
      Triple(
          acc.first.copyOf().also { it[acc.second] += 1 },
          acc.second + acc.first[acc.second],
          acc.third + 1
      )
    }
        .first { it.second !in values.indices }
        .third
  }

  override fun second(data: String): Any? {
    val values = data.lines()
        .map { it.toInt() }
        .toTypedArray()

    return generateSequence(0) { it + 1 }.scan(Triple(values, 0, 0)) { acc, _ ->
      Triple(
          acc.first.copyOf().also { it[acc.second] += if (it[acc.second] < 3) 1 else -1 },
          acc.second + acc.first[acc.second],
          acc.third + 1
      )
    }
        .first { it.second !in values.indices }
        .third
  }
}

fun main() = SomeDay.mainify(Day5::class)
