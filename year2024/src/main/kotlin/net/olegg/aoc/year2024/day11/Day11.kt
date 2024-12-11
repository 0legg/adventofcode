package net.olegg.aoc.year2024.day11

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseBigInts
import net.olegg.aoc.year2024.DayOf2024
import java.math.BigInteger

/**
 * See [Year 2024, Day 11](https://adventofcode.com/2024/day/11)
 */
object Day11 : DayOf2024(11) {
  private val mult = 2024.toBigInteger()

  override fun first(): Any? {
    val start = data.parseBigInts(" ")

    return solve(start, 25)
  }

  override fun second(): Any? {
    val start = data.parseBigInts(" ")

    return solve(start, 75)
  }

  private fun solve(input: List<BigInteger>, steps: Int): Long {
    val start = input.groupingBy { it }.eachCount().mapValues { it.value.toLong() }
    return (1..steps).fold(start) { acc, _ ->
      acc
        .flatMap { (stone, count) ->
          val string = stone.toString()
          when {
            string == "0" -> listOf(BigInteger.ONE to count)
            string.length % 2 == 0 -> listOf(
              BigInteger(string.take(string.length / 2)) to count,
              BigInteger(string.drop(string.length / 2)) to count,
            )
            else -> listOf(stone * mult to count)
          }
        }
        .groupingBy { it.first }
        .fold(0) { sum, (_, count) -> sum + count }
    }.values.sum()
  }
}

fun main() = SomeDay.mainify(Day11)
