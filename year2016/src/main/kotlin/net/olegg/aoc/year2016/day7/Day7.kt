package net.olegg.aoc.year2016.day7

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 7](https://adventofcode.com/2016/day/7)
 */
object Day7 : DayOf2016(7) {
  private val ABBA = ('a'..'z')
    .flatMap { a -> ('a'..'z').filter { b -> a != b }.map { b -> "$a$b$b$a" } }
  private val ABABAB = ('a'..'z')
    .flatMap { a -> ('a'..'z').filter { b -> a != b }.map { b -> "$a$b$a" to "$b$a$b" } }

  private fun splitAddresses(addresses: List<String>): List<Pair<List<String>, List<String>>> {
    return addresses.map { it.split("[", "]") }
      .map { tokens -> tokens.mapIndexed { i, s -> i to s }.partition { it.first % 2 == 0 } }
      .map { (outer, inner) -> outer.map { it.second } to inner.map { it.second } }
  }

  override fun first(): Any? {
    return splitAddresses(lines)
      .filterNot { (_, inner) -> inner.any { token -> ABBA.any { it in token } } }
      .count { (outer, _) -> outer.any { token -> ABBA.any { it in token } } }
  }

  override fun second(): Any? {
    return splitAddresses(lines)
      .count { (outer, inner) ->
        ABABAB.any { ab ->
          outer.any { ab.first in it } && inner.any { ab.second in it }
        }
      }
  }
}

fun main() = SomeDay.mainify(Day7)
