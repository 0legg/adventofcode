package net.olegg.aoc.year2016.day18

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 18](https://adventofcode.com/2016/day/18)
 */
object Day18 : DayOf2016(18) {

  private val patterns = listOf(
    "\\^\\^\\.".toRegex(),
    "\\.\\^\\^".toRegex(),
    "\\^\\.\\.".toRegex(),
    "\\.\\.\\^".toRegex()
  )

  override fun first(): Any? {
    return solve(40)
  }

  override fun second(): Any? {
    return solve(400000)
  }

  fun solve(rows: Int): Int {
    return (1 until rows).fold(".$data." to data.count { it == '.' }) { acc, _ ->
      val traps = patterns.flatMap { pattern -> pattern.findAll(acc.first).map { it.range.first + 1 }.toList() }
      val row = acc.first.indices.map { if (traps.contains(it)) '^' else '.' }.joinToString(separator = "")
      return@fold row to acc.second + row.count { it == '.' } - 2
    }.second
  }
}

fun main() = SomeDay.mainify(Day18)
