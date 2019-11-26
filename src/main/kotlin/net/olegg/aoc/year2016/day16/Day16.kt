package net.olegg.aoc.year2016.day16

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 16](https://adventofcode.com/2016/day/16)
 */
object Day16 : DayOf2016(16) {
  override fun first(data: String): Any? {
    return checksum(data, 272)
  }

  override fun second(data: String): Any? {
    return checksum(data, 35651584)
  }

  fun checksum(initial: String, length: Int): String {
    val curve =
        generateSequence(initial) { prev ->
          prev + "0" + prev.reversed().replace('0', '2').replace('1', '0').replace('2', '1')
        }
            .dropWhile { it.length <= length }
            .first()
            .substring(0, length)

    return generateSequence(curve) { prev ->
      prev.asSequence()
          .chunked(2)
          .joinTo(StringBuilder(), separator = "") { if (it[0] == it[1]) "1" else "0" }
          .toString()
    }
        .first { it.length % 2 == 1 }
  }
}

fun main() = SomeDay.mainify(Day16)
