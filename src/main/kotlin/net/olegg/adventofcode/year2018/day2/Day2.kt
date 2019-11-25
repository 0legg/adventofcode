package net.olegg.adventofcode.year2018.day2

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018

/**
 * See [Year 2018, Day 2](https://adventofcode.com/2018/day/2)
 */
class Day2 : DayOf2018(2) {
  override fun first(data: String): Any? {
    val freqs = data
        .trim()
        .lines()
        .map { char -> char.groupBy { it } }
        .map { entry -> entry.mapValues { it.value.size } }

    return freqs.count { it.containsValue(2) } * freqs.count { it.containsValue(3) }
  }

  override fun second(data: String): Any? {
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

fun main(args: Array<String>) = SomeDay.mainify(Day2::class)
