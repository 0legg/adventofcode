package net.olegg.aoc.year2024.day11

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseBigInts
import net.olegg.aoc.year2024.DayOf2024
import java.math.BigInteger

/**
 * See [Year 2024, Day 11](https://adventofcode.com/2024/day/11)
 */
object Day11 : DayOf2024(11) {
  override fun first(): Any? {
    val start = data.parseBigInts(" ")
    val mult = 2024.toBigInteger()

    return (1..25).fold(start) { acc, _ ->
      acc.flatMap { stone ->
        val string = stone.toString()
        when {
          string == "0" -> listOf(BigInteger.ONE)
          string.length % 2 == 0 -> listOf(
            BigInteger(string.take(string.length / 2)),
            BigInteger(string.drop(string.length / 2)),
          )
          else -> listOf(stone * mult)
        }
      }
    }.size
  }
}

fun main() = SomeDay.mainify(Day11)
