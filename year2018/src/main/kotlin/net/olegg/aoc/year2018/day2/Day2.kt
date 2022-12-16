package net.olegg.aoc.year2018.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.pairs
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 2](https://adventofcode.com/2018/day/2)
 */
object Day2 : DayOf2018(2) {
  override fun first(): Any? {
    val freqs = lines.map { char ->
      char.groupingBy { it }.eachCount()
    }

    return freqs.count { it.containsValue(2) } * freqs.count { it.containsValue(3) }
  }

  override fun second(): Any? {
    return lines.pairs()
      .map { (first, second) ->
        val diff = first.filterIndexed { pos, char -> second[pos] == char }
        Triple(first, second, diff)
      }
      .first { (first, second, diff) ->
        diff.length == first.length - 1 && diff.length == second.length - 1
      }
      .third
  }
}

fun main() = SomeDay.mainify(Day2)
