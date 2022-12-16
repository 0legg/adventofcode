package net.olegg.aoc.year2016.day22

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 22](https://adventofcode.com/2016/day/22)
 */
object Day22 : DayOf2016(22) {
  val pattern = "/dev/grid/node-x(\\d+)-y(\\d+)\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)T\\s+(\\d+)%".toRegex()

  override fun first(): Any? {
    val machines = lines
      .mapNotNull { line ->
        pattern.matchEntire(line)
          ?.groupValues
          ?.let { values ->
            values.subList(1, 6).map { it.toInt() }
          }
      }

    return machines
      .filter { it[3] != 0 }
      .flatMap { a ->
        machines.filter { it != a }
          .filter { it[4] >= a[3] }
          .map { b -> a to b }
      }
      .size
  }

  override fun second(): Any? {
    val machines = lines
      .mapNotNull { line ->
        pattern.matchEntire(line)
          ?.groupValues
          ?.let { values ->
            values.subList(1, 6).map { it.toInt() }
          }
      }

    return machines
      .groupByTo(sortedMapOf()) { it[1] }
      .map { (_, row) ->
        row.sortedBy { it[0] }.joinToString(separator = "") { cell ->
          when (cell[3]) {
            0 -> "_"
            in 1..100 -> "."
            else -> "#"
          }
        }
      }
      .joinToString(separator = "\n", prefix = "\n")
  }
}

fun main() = SomeDay.mainify(Day22)
