package net.olegg.aoc.year2023.day4

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 4](https://adventofcode.com/2023/day/4)
 */
object Day4 : DayOf2023(4) {
  private val GAME_PATTERN = "Game \\d+: ".toRegex()

  override fun first(): Any? {
    return lines
      .asSequence()
      .map { line -> line.replace(GAME_PATTERN, "") }
      .map { it.split("|").toPair() }
      .map { it.first.parseInts(" ") to it.second.parseInts(" ") }
      .map { it.second.count { my -> my in it.first } }
      .sumOf { if (it == 0) 0 else (1 shl (it - 1)) }
  }

  override fun second(): Any? {
    val cards = lines
      .map { line -> line.replace(GAME_PATTERN, "") }
      .map { it.split("|").toPair() }
      .map { it.first.parseLongs(" ") to it.second.parseLongs(" ") }
      .map { (good, mine) ->
        mine.count { my -> my in good }
      }

    return cards.foldIndexed(List(cards.size) { 1L }) { index, acc, card ->
      val count = acc[index]
      val add = List(cards.size) { if (it in (index + 1..index + card)) count else 0 }
      acc.zip(add, Long::plus)
    }.sum()
  }
}

fun main() = SomeDay.mainify(Day4)
