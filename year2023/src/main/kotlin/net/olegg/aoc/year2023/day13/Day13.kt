package net.olegg.aoc.year2023.day13

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 13](https://adventofcode.com/2023/day/13)
 */
object Day13 : DayOf2023(13) {
  override fun first(): Any? {
    val patterns = data.split("\n\n").map { pattern -> pattern.lines().map { it.toList() } }
    val transposed = patterns.map { it.transpose() }

    return transposed.sumOf { it.rowsBeforeReflection() } + 100 * patterns.sumOf { it.rowsBeforeReflection() }
  }

  override fun second(): Any? {
    val patterns = data.split("\n\n").map { pattern -> pattern.lines().map { it.toList() } }
    val transposed = patterns.map { it.transpose() }

    return transposed.sumOf { it.rowsBeforeSmudge() } + 100 * patterns.sumOf { it.rowsBeforeSmudge() }
  }

  private fun List<List<Char>>.transpose(): List<List<Char>> {
    return List(first().size) { row ->
      List(size) { column -> this[column][row] }
    }
  }

  private fun List<List<Char>>.rowsBeforeReflection(): Int {
    val rev = asReversed()
    for (i in 1..<size) {
      val below = drop(i)
      val above = rev.takeLast(i)

      if (above.zip(below).all { (a, b) -> a == b }) {
        return i
      }
    }
    return 0
  }

  private fun List<List<Char>>.rowsBeforeSmudge(): Int {
    val rev = asReversed()
    for (i in 1..<size) {
      val below = drop(i)
      val above = rev.takeLast(i)

      if (above.zip(below).sumOf { (a, b) -> a.zip(b).count { (ca, cb) -> ca != cb } } == 1) {
        return i
      }
    }
    return 0
  }
}

fun main() = SomeDay.mainify(Day13)
