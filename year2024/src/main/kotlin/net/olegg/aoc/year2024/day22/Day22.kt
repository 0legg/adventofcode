package net.olegg.aoc.year2024.day22

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 22](https://adventofcode.com/2024/day/22)
 */
object Day22 : DayOf2024(22) {
  private val modulo = 16777215L

  override fun first(): Any? {
    return lines.map { it.toLong() }
      .sumOf { number ->
        generateSequence(number) { curr ->
          val first = (curr xor (curr shl 6)) and modulo
          val second = (first xor (first shr 5)) and modulo
          val third = (second xor (second shl 11)) and modulo
          third
        }
          .drop(2000)
          .first()
      }
  }

  override fun second(): Any? {
    val prices = lines
      .map { it.toLong() }
      .map { number ->
        val nums = generateSequence(number) { curr ->
          val first = (curr xor (curr shl 6)) and modulo
          val second = (first xor (first shr 5)) and modulo
          val third = (second xor (second shl 11)) and modulo
          third
        }
          .map { curr -> curr % 10 }
          .take(2001)
          .toList()

        val diffs = nums.zipWithNext { a, b -> b - a }

        val pairs = diffs.windowed(4).asReversed().zip(nums.asReversed()).reversed()

        pairs.groupBy { it.first }.mapValues { it.value.first().second }
      }

    val allDiffs = prices.flatMap { currPrices -> currPrices.keys }.toSet()

    return allDiffs.maxOf { diff ->
      prices.sumOf { currPrices -> (currPrices[diff] ?: 0) }
    }
  }
}

fun main() = SomeDay.mainify(Day22)
