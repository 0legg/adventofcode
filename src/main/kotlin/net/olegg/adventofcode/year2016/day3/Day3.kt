package net.olegg.adventofcode.year2016.day3

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/3">Year 2016, Day 3</a>
 */
class Day3 : DayOf2016(3) {
  companion object {
    val DIGITS = "\\d+".toRegex()
  }

  override fun first(data: String): Any? {
    return data
        .lines()
        .map { line ->
          DIGITS.findAll(line).map { it.value.toInt() }.toList().sorted()
        }
        .filter { it.size == 3 }
        .count { it[0] + it[1] > it[2] }
  }

  override fun second(data: String): Any? {
    val rows = data
        .lines()
        .map { line ->
          DIGITS.findAll(line).map { it.value.toInt() }.toList()
        }

    val columns = (1..rows.size / 3)
        .map { it * 3 }
        .map { rows.take(it).takeLast(3) }
        .flatMap { matrix ->
          listOf(
              listOf(matrix[0][0], matrix[1][0], matrix[2][0]),
              listOf(matrix[0][1], matrix[1][1], matrix[2][1]),
              listOf(matrix[0][2], matrix[1][2], matrix[2][2]))
        }
        .filter { it.size == 3 }
        .map { it.sorted() }

    return columns.count { it[0] + it[1] > it[2] }
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day3::class)
