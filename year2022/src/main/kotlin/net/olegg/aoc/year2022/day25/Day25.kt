package net.olegg.aoc.year2022.day25

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 25](https://adventofcode.com/2022/day/25)
 */
object Day25 : DayOf2022(25) {
  override fun first(): Any? {
    val numbers = lines
      .map { line ->
        line.fold(0L) { acc, char ->
          acc * 5 + when (char) {
            '2' -> 2
            '1' -> 1
            '0' -> 0
            '-' -> -1
            '=' -> -2
            else -> error("Not a SNAFU")
          }
        }
      }

    val sum = numbers.sum()
    val snafu = buildString {
      var curr = sum
      while (curr != 0L) {
        val rem = curr % 5
        curr /= 5
        when (rem) {
          in 0L..2L -> append(rem)
          3L -> {
            append('=')
            curr++
          }
          4L -> {
            append('-')
            curr++
          }
        }
      }
    }

    return snafu.reversed()
  }
}

fun main() = SomeDay.mainify(Day25)
