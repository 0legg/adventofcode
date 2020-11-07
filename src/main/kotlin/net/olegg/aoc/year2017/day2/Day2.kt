package net.olegg.aoc.year2017.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 2](https://adventofcode.com/2017/day/2)
 */
object Day2 : DayOf2017(2) {
  override fun first(data: String): Any? {
    return data
        .trim()
        .lines()
        .map { it.parseInts() }
        .map { (it.maxOrNull() ?: 0) - (it.minOrNull() ?: 0) }
        .sum()
  }

  override fun second(data: String): Any? {
    return data
        .trim()
        .lines()
        .map { it.parseInts() }
        .map { list ->
          list.flatMap { first ->
            list.filter { first % it == 0 }
                .filter { it != first }
                .map { first to it }
          }
        }
        .map { it.first() }
        .map { it.first / it.second }
        .sum()
  }
}

fun main() = SomeDay.mainify(Day2)
