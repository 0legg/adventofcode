package net.olegg.aoc.year2020.day10

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 10](https://adventofcode.com/2020/day/10)
 */
object Day10 : DayOf2020(10) {
  override fun first(): Any? {
    val adapters = data.parseInts(delimiters = "\n").sorted()
    val jolts = listOf(0) + adapters + listOf(adapters.last() + 3)

    val diffs = jolts
      .zipWithNext()
      .map { it.second - it.first }
      .groupingBy { it }
      .eachCount()

    return diffs.getValue(1) * diffs.getValue(3)
  }

  override fun second(): Any? {
    val adapters = data.parseInts(delimiters = "\n").sorted()
    val jolts = listOf(0) + adapters + listOf(adapters.last() + 3)

    val options = jolts.associateWithTo(mutableMapOf()) { 0L }
    options[0] = 1L

    jolts.forEach { value ->
      for (add in 1..3) {
        val next = add + value
        if (next in options) {
          options[next] = options[next]!! + options[value]!!
        }
      }
    }

    return options[jolts.last()]
  }
}

fun main() = SomeDay.mainify(Day10)
