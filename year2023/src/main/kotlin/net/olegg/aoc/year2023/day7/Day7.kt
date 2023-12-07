package net.olegg.aoc.year2023.day7

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 7](https://adventofcode.com/2023/day/7)
 */
object Day7 : DayOf2023(7) {
  private val JOKERS = "23456789TQKA".toList()

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

  override fun second(): Any? {
    val order = "J23456789TQKA"
    return lines.map { it.split(" ").toPair() }
      .sortedWith(
        compareBy(
          { (card, _) -> getStrengthJoker(card) },
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

  private fun getStrengthJoker(card: String): Int {
    val options = card.map {
      if (it == 'J') JOKERS else listOf(it)
    }

    val combinations = options.fold(1) { acc, value -> acc * value.size }

    return generateSequence(0) { it + 1 }
      .takeWhile { it < combinations }
      .map { combo ->
        options.fold(combo to emptyList<Char>()) { (rem, head), option ->
          rem / option.size to head + option[rem % option.size]
        }.second.joinToString("")
      }
      .maxOf { getStrength(it) }
  }

  private fun <T> List<T>.second() = get(1)
}

fun main() = SomeDay.mainify(Day7)
