package net.olegg.aoc.year2023.day12

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 12](https://adventofcode.com/2023/day/12)
 */
object Day12 : DayOf2023(12) {
  private val DOTS = "\\.+".toRegex()
  private val EMPTY = setOf('.', '?', null)
  private val FILLED = setOf('#', '?')

  override fun first(): Any? {
    return lines
      .map { line -> line.split(" ").toPair() }
      .map { (line, counts) ->
        line to counts.parseInts(",")
      }
      .sumOf { (line, counts) ->
        count(line, counts)
      }
  }

  override fun second(): Any? {
    return lines
      .map { line -> line.split(" ").toPair() }
      .map { (line, counts) ->
        line to counts.parseInts(",")
      }
      .map { (line, counts) ->
        List(5) { line }.joinToString(separator = "?") to List(5) { counts }.flatten()
      }
      .sumOf { (line, counts) ->
        count(line, counts)
      }
  }

  private fun count(line: String, counts: List<Int>): Long {
    val dyn = Array(counts.size + 1) { LongArray(line.length + 1) { 0 } }

    dyn[0][line.length] = 1
    line.withIndex()
      .reversed()
      .takeWhile { it.value != '#' }
      .forEach { dyn[0][it.index] = 1 }

    for (j in 1..counts.size) {
      val count = counts[counts.size - j]
      val fix = if (j == 1) 0 else 1
      for (i in line.length - 1 downTo 0) {
        if (line[i] in EMPTY) {
          dyn[j][i] += dyn[j].getOrElse(i + 1) { 0 }
        }
        if (line[i] in FILLED) {
          if (line.getOrNull(i - 1) in EMPTY &&
            (i + 1..<i + count).all { line.getOrNull(it) in FILLED } &&
            line.getOrNull(i + count) in EMPTY) {
            dyn[j][i] += dyn[j - 1].getOrElse(i + count + fix) { 0 }
          }
        }
      }
    }

    return dyn.last().first()
  }
}

fun main() = SomeDay.mainify(Day12)
