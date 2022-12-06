package net.olegg.aoc.year2020.day9

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 9](https://adventofcode.com/2020/day/9)
 */
object Day9 : DayOf2020(9) {
  override fun first(): Any? {
    val nums = data.parseLongs("\n")

    return nums.windowed(26)
      .map { it.dropLast(1) to it.last() }
      .map { (head, tail) -> head.flatMapIndexed { i, x -> head.drop(i + 1).map { it + x } }.toSet() to tail }
      .first { (head, tail) -> tail !in head }
      .second
  }

  override fun second(): Any? {
    val nums = data.parseLongs("\n")

    val bad = nums.windowed(26)
      .map { it.dropLast(1) to it.last() }
      .map { (head, tail) -> head.flatMapIndexed { i, x -> head.drop(i + 1).map { it + x } }.toSet() to tail }
      .first { (head, tail) -> tail !in head }
      .second

    val partials = nums.scan(0L) { acc, value -> acc + value }
    var from = 0
    var to = 2

    while (to in partials.indices) {
      val curr = partials[to] - partials[from]
      when {
        curr < bad -> to++
        curr > bad -> {
          from++
          to = maxOf(to, from + 2)
        }
        else -> break
      }
    }

    val subset = nums.subList(from, to)
    return subset.minOf { it } + subset.maxOf { it }
  }
}

fun main() = SomeDay.mainify(Day9)
