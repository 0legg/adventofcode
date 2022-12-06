package net.olegg.aoc.year2015.day11

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.series
import net.olegg.aoc.year2015.DayOf2015
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

/**
 * See [Year 2015, Day 11](https://adventofcode.com/2015/day/11)
 */
object Day11 : DayOf2015(11) {
  private val ALPHABET = 'a'..'z'
  private val MAPPINGS = ALPHABET
    .withIndex()
    .associate { (index, char) -> char to index.toBigInteger() }
  private val UNMAPPINGS = ALPHABET
    .withIndex()
    .associate { (index, char) -> index.toBigInteger() to char }
  private val TRIPLES = ALPHABET
    .windowed(3)
    .map { it.joinToString(separator = "") }
  private val SHIFT = 26.toBigInteger()

  private fun wrap(password: String): BigInteger {
    return password.fold(ZERO) { acc, char -> acc * SHIFT + (MAPPINGS[char] ?: ZERO) }
  }

  private fun unwrap(wrapped: BigInteger): String {
    return buildString {
      var curr = wrapped
      do {
        val (next, rem) = curr.divideAndRemainder(SHIFT)
        append(UNMAPPINGS[rem] ?: "?")
        curr = next
      } while (curr > ZERO)
      reverse()
    }
  }

  private fun passwordList(password: String) = generateSequence(wrap(password)) { it + ONE }.map { unwrap(it) }

  private fun password(password: String): String {
    return passwordList(password)
      .drop(1)
      .filterNot { string -> "iol".any { it in string } }
      .filter { string -> TRIPLES.any { it in string } }
      .filter { string ->
        string
          .toList()
          .series()
          .filter { it.second > 1 }
          .sumOf { it.second } > 3
      }
      .first()
  }

  override fun first(): Any? {
    return password(data)
  }

  override fun second(): Any? {
    return password(password(data))
  }
}

fun main() = SomeDay.mainify(Day11)
