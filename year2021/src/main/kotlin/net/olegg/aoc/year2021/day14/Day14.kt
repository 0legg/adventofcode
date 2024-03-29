package net.olegg.aoc.year2021.day14

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2021.DayOf2021
import java.math.BigInteger

/**
 * See [Year 2021, Day 14](https://adventofcode.com/2021/day/14)
 */
object Day14 : DayOf2021(14) {
  override fun first(): Any? {
    return solve(10)
  }

  override fun second(): Any? {
    return solve(40)
  }

  private fun solve(steps: Int): BigInteger {
    val (rawStart, rawPatterns) = data.split("\n\n")

    val patterns = rawPatterns.lines()
      .map { it.split(" -> ").toPair() }
      .associate { it.first to setOf("${it.first.first()}${it.second}", "${it.second}${it.first.last()}") }

    val start = rawStart.windowed(2)
      .groupingBy { it }
      .eachCount()
      .mapValues { it.value.toBigInteger() }

    val end = (1..steps).fold(start) { acc, _ ->
      val next = mutableMapOf<String, BigInteger>()

      acc.forEach { (chunk, size) ->
        (patterns[chunk] ?: setOf(chunk)).forEach { newChunk ->
          next[newChunk] = next.getOrDefault(newChunk, BigInteger.ZERO) + size
        }
      }

      next.toMap()
    }

    val chars = end.toList()
      .flatMap { listOf(it.first.first() to it.second, it.first.last() to it.second) }
    val doubleChars = chars + listOf(rawStart.first() to BigInteger.ONE, rawStart.last() to BigInteger.ONE)
    val charMap = doubleChars.groupBy { it.first }
      .mapValues { entry -> entry.value.sumOf { it.second } }

    return (charMap.maxOf { it.value } - charMap.minOf { it.value }).divide(BigInteger.TWO)
  }
}

fun main() = SomeDay.mainify(Day14)
