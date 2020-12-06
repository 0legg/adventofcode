package net.olegg.aoc.year2020.day7

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 7](https://adventofcode.com/2020/day/7)
 */
object Day7 : DayOf2020(7) {
  private val PATTERN = "(\\d+) ([^\\d]*) bags?".toRegex()
  private val MINE = "shiny gold"

  override fun first(data: String): Any? {
    val rules = data
      .trim()
      .lines()
      .map { it.split(" bags contain ").toPair() }
      .map { (outer, inner) -> outer to PATTERN.findAll(inner).map { it.groupValues[2] }.toList() }

    val reverse = rules.flatMap { (outer, inner) -> inner.map { it to outer } }
      .groupBy(keySelector = { it.first }, valueTransform = { it.second })

    val visited = mutableSetOf<String>()
    val queue = ArrayDeque(listOf(MINE))

    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      if (curr !in visited) {
        visited += curr
        queue += reverse[curr].orEmpty().filter { it !in visited }
      }
    }

    return visited.size - 1
  }
}

fun main() = SomeDay.mainify(Day7)
