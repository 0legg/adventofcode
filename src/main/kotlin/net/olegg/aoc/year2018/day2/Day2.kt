package net.olegg.aoc.year2018.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 2](https://adventofcode.com/2018/day/2)
 */
object Day2 : DayOf2018(2) {
  override fun first(): Any? {
    val freqs = data
      .trim()
      .lines()
      .map { char -> char.groupBy { it } }
      .map { entry -> entry.mapValues { it.value.size } }

    return freqs.count { it.containsValue(2) } * freqs.count { it.containsValue(3) }
  }

  override fun second(): Any? {
    val names = data
      .trim()
      .lines()

    names.forEach { first ->
      names.forEach { second ->
        val diff = first.filterIndexed { pos, char -> second[pos] == char }
        if (diff.length == first.length - 1) {
          return diff
        }
      }
    }
    return null
  }
}

fun main() = SomeDay.mainify(Day2)
