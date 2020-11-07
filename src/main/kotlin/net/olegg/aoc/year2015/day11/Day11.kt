package net.olegg.aoc.year2015.day11

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.series
import net.olegg.aoc.year2015.DayOf2015
import java.math.BigInteger

/**
 * See [Year 2015, Day 11](https://adventofcode.com/2015/day/11)
 */
object Day11 : DayOf2015(11) {
  private val MAPPINGS = ('a'..'z')
      .mapIndexed { index, char -> char to BigInteger.valueOf(index.toLong()) }
      .toMap()
  private val UNMAPPINGS = ('a'..'z')
      .mapIndexed { index, char -> BigInteger.valueOf(index.toLong()) to char }
      .toMap()
  private val TRIPLES = ('a'..'x').map { String(charArrayOf(it, it + 1, it + 2)) }
  private val SHIFT = BigInteger.valueOf(26)

  private fun wrap(password: String): BigInteger {
    return password.fold(BigInteger.ZERO) { acc, char -> acc * SHIFT + (MAPPINGS[char] ?: BigInteger.ZERO) }
  }

  private fun unwrap(wrapped: BigInteger): String {
    val sb = StringBuilder()
    var curr = wrapped
    do {
      val (next, rem) = curr.divideAndRemainder(SHIFT)
      sb.append(UNMAPPINGS[rem] ?: "?")
      curr = next
    } while (curr > BigInteger.ZERO)
    return sb.reverse().toString()
  }

  fun passwordList(password: String) = generateSequence(wrap(password)) { it + BigInteger.ONE }.map { unwrap(it) }

  fun password(password: String): String {
    return passwordList(password)
        .drop(1)
        .filterNot { string ->
          "iol".any { string.contains(it) }
        }
        .filter { string ->
          TRIPLES.any { string.contains(it) }
        }
        .filter { string ->
          string
              .toList()
              .series()
              .filter { it.size > 1 }
              .flatten()
              .joinToString(separator = "")
              .length > 3
        }
        .first()
  }

  override fun first(data: String): Any? {
    return password(data.trim())
  }

  override fun second(data: String): Any? {
    return password(password(data.trim()))
  }
}

fun main() = SomeDay.mainify(Day11)
