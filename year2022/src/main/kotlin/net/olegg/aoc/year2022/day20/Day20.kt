package net.olegg.aoc.year2022.day20

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2022.DayOf2022
import java.util.LinkedList

/**
 * See [Year 2022, Day 20](https://adventofcode.com/2022/day/20)
 */
object Day20 : DayOf2022(20) {
  override fun first(): Any? {
    val numbers = lines
      .mapIndexed { index, line -> line.toInt() to index }
      .toMutableList()
    val mod = numbers.size - 1

    numbers.indices.forEach { index ->
      val positionBefore = numbers.indexOfFirst { it.second == index }
      val value = numbers[positionBefore]
      val positionAfter = (((positionBefore + value.first) % mod) + mod) % mod
      numbers.removeAt(positionBefore)
      numbers.add(positionAfter, value)
    }

    val position = numbers.indexOfFirst { it.first == 0 }

    return numbers[(position + 1000) % numbers.size].first +
      numbers[(position + 2000) % numbers.size].first +
      numbers[(position + 3000) % numbers.size].first
  }
}

fun main() = SomeDay.mainify(Day20)
