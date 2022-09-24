package net.olegg.aoc.year2015.day17

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 17](https://adventofcode.com/2015/day/17)
 */
object Day17 : DayOf2015(17) {
  private val containers = data.trim().lines().map { it.toInt() }
  override fun first(): Any? {
    return containers.fold(listOf(1) + List(150) { 0 }) { acc, container ->
      acc.mapIndexed { index, value ->
        if (index < container) value else value + acc[index - container]
      }
    }.last()
  }

  override fun second(): Any? {
    return (0 until 1.shl(containers.size))
      .map { value ->
        containers.mapIndexed { index, container -> value.shr(index).and(1) * container }
      }
      .filter { it.sum() == 150 }
      .groupBy { it.count { size -> size != 0 } }
      .minByOrNull { it.key }
      ?.value
      ?.size
  }
}

fun main() = SomeDay.mainify(Day17)
