package net.olegg.aoc.year2020.day16

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 16](https://adventofcode.com/2020/day/16)
 */
object Day16 : DayOf2020(16) {
  private val RANGE_PATTERN = ".*: (\\d+)-(\\d+) or (\\d+)-(\\d+)".toRegex()
  override fun first(): Any? {
    val (ranges, yours, nearby) = data.split("\n\n")

    val valid = mutableSetOf<Int>()

    ranges
      .lines()
      .flatMap { line -> RANGE_PATTERN.find(line)?.groupValues?.drop(1).orEmpty() }
      .map { it.toInt() }
      .zipWithNext()
      .forEach { (from, to) -> (from..to).forEach { valid += it } }

    val invalid = mutableListOf<Int>()
    invalid += yours
      .lines()
      .drop(1)
      .flatMap { it.parseInts(",") }
      .filter { it !in valid }

    invalid += nearby
      .lines()
      .drop(1)
      .flatMap { it.parseInts(",") }
      .filter { it !in valid }

    return invalid.sum()
  }

  override fun second(): Any? {
    val (ranges, yours, nearby) = data.split("\n\n")

    val validValues = mutableSetOf<Int>()

    val rangeValues = ranges
      .lines()
      .map { line ->
        RANGE_PATTERN.find(line)
          ?.groupValues
          ?.drop(1)
          .orEmpty()
          .map { it.toInt() }
          .zipWithNext { a, b -> a..b }
          .toPair()
      }

    rangeValues.forEach {
      validValues += it.first
      validValues += it.second
    }

    val yourTicket = yours
      .lines()
      .last()
      .parseLongs(",")

    val validTickets = nearby
      .lines()
      .drop(1)
      .map { it.parseInts(",") }
      .filter { line -> line.all { it in validValues } }

    val possiblePositions = rangeValues
      .withIndex()
      .associateTo(mutableMapOf()) { (column, range) ->
        column to yourTicket.indices
          .asSequence()
          .map { col ->
            col to validTickets.map { it[col] }
          }
          .filter { (_, col) -> col.all { it in range.first || it in range.second } }
          .mapTo(mutableSetOf()) { it.first }
      }

    val fixedPositions = mutableMapOf<Int, Int>()

    while (possiblePositions.isNotEmpty() && possiblePositions.any { it.value.size == 1 }) {
      val fixed = possiblePositions.entries.first { it.value.size == 1 }
      val fixedValue = fixed.value.first()
      fixedPositions[fixed.key] = fixedValue
      possiblePositions.remove(fixed.key)
      possiblePositions.forEach {
        it.value -= fixedValue
      }
    }

    val permutation = yourTicket.indices.map { yourTicket[fixedPositions[it] ?: 0] }

    return ranges.lines()
      .zip(permutation)
      .filter { it.first.startsWith("departure") }
      .map { it.second }
      .reduce(Long::times)
  }
}

fun main() = SomeDay.mainify(Day16)
