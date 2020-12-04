package net.olegg.aoc.year2020.day4

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 4](https://adventofcode.com/2020/day/4)
 */
object Day4 : DayOf2020(4) {
  override fun first(data: String): Any? {
    val passports = data
      .trim()
      .split("\n\n")
      .map { it.replace("\n", " ") }
      .map { line -> line.split(" ").associate { it.split(":").toPair() } }
    return passports.count { (it - "cid").size == 7 }
  }

  fun <T> List<T>.toPair(): Pair<T, T> = first() to last()
}

fun main() = SomeDay.mainify(Day4)
