package net.olegg.aoc.year2023.day7

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 7](https://adventofcode.com/2023/day/7)
 */
object Day7 : DayOf2023(7) {
  override fun first(): Any? {
    val order = "23456789TJQKA"

    return lines.map { it.split(" ").toPair() }
      .sortedWith(
        compareBy(
          { (card, _) -> getStrength(card) },
          { (card, _) -> card.fold(0) { acc, value -> acc * order.length + order.indexOf(value) } },
        ),
      )
      .mapIndexed { index, (_, cost) -> (index + 1) * cost.toLong() }
      .sum()
  }

  private fun getStrength(card: String): Int {
    val counts = card.groupingBy { it }
      .eachCount()
      .values
      .sortedDescending()

    return when {
      counts.first() == 5 -> 7
      counts.first() == 4 -> 6
      counts.first() == 3 && counts.second() == 2 -> 5
      counts.first() == 3 -> 4
      counts.first() == 2 && counts.second() == 2 -> 3
      counts.first() == 2 -> 2
      else -> 1
    }
  }

  private fun <T> List<T>.second() = get(1)
}

fun main() = SomeDay.mainify(Day7)
