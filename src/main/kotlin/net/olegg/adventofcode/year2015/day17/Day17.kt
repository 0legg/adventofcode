package net.olegg.adventofcode.year2015.day17

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2015.DayOf2015

/**
 * See [Year 2015, Day 17](https://adventofcode.com/2015/day/17)
 */
class Day17 : DayOf2015(17) {
  val containers = data.lines().map { it.toInt() }
  override fun first(data: String): Any? {
    return containers.fold(listOf(1) + Array(150) { 0 }.toList()) { acc, container ->
      acc.mapIndexed { index, value ->
        if (index < container) value else value + acc[index - container]
      }
    }.last()
  }

  override fun second(data: String): Any? {
    return (0..(1.shl(containers.size) - 1)).map { value ->
      containers.mapIndexed { index, container -> value.shr(index).and(1) * container }
    }.filter { it.sum() == 150 }.groupBy { it.count { it != 0 } }.minBy { it.key }?.value?.size
  }
}

fun main() = SomeDay.mainify(Day17::class)
