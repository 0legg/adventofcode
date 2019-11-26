package net.olegg.adventofcode.year2018.day1

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018

/**
 * See [Year 2018, Day 1](https://adventofcode.com/2018/day/1)
 */
class Day1 : DayOf2018(1) {
  override fun first(data: String): Any? {
    return data
        .trim()
        .lines()
        .map { it.removePrefix("+") }
        .map { it.toLong() }
        .sum()
  }

  override fun second(data: String): Any? {
    val shifts = data
        .trim()
        .lines()
        .map { it.removePrefix("+") }
        .map { it.toLong() }

    val history = mutableSetOf<Long>()

    return generateSequence(0L to 0) { (prev, index) ->
      val next = prev + shifts[index]
      return@generateSequence next to (index + 1) % shifts.size
    }
        .takeWhile { it.first !in history }
        .onEach { history += it.first }
        .last()
        .let { (prev, index) -> prev + shifts[index] }
  }
}

fun main() = SomeDay.mainify(Day1::class)
