package net.olegg.aoc.year2024.day3

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 3](https://adventofcode.com/2024/day/3)
 */
object Day3 : DayOf2024(3) {
  private val pattern = """mul\((\d+),(\d+)\)""".toRegex()
  private val dos = """do\(\)""".toRegex()
  private val donts = """don't\(\)""".toRegex()

  override fun first(): Any? {
    return pattern.findAll(data).sumOf { match ->
      val (a, b) = match.destructured
      a.toLong() * b.toLong()
    }
  }

  override fun second(): Any? {
    val mults = buildList {
      add(0 to 1)
      dos.findAll(data).forEach { add(it.range.first to 1) }
      donts.findAll(data).forEach { add(it.range.first to 0) }
    }.sortedBy { it.first }

    return pattern.findAll(data).sumOf { match ->
      val start = match.range.first
      val (a, b) = match.destructured

      a.toLong() * b.toLong() * mults.last { it.first < start }.second
    }
  }
}

fun main() = SomeDay.mainify(Day3)
