package net.olegg.aoc.year2019.day22

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2019.DayOf2019

/**
 * See [Year 2019, Day 22](https://adventofcode.com/2019/day/22)
 */
object Day22 : DayOf2019(22) {
  override fun first(data: String): Any? {
    val shuffle = data
        .trim()
        .lines()

    val startDeck = List(10007) { it }

    val finalDeck = shuffle.fold(startDeck) { deck, operation ->
      return@fold when {
        operation == "deal into new stack" -> {
          deck.reversed()
        }
        operation.startsWith("cut") -> {
          val num = (deck.size + operation.split(" ")[1].toInt()) % deck.size
          deck.drop(num) + deck.take(num)
        }
        else -> {
          val num = operation.split(" ").last().toInt()
          generateSequence(0) { (it + num) % deck.size }
              .take(deck.size)
              .toList()
              .zip(deck)
              .sortedBy { it.first }
              .map { it.second }
        }
      }
    }

    return finalDeck.indexOf(2019)
  }
}

fun main() = SomeDay.mainify(Day22)
