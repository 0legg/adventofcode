package net.olegg.aoc.year2016.day14

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.md5
import net.olegg.aoc.year2016.DayOf2016

/**
 * See [Year 2016, Day 14](https://adventofcode.com/2016/day/14)
 */
object Day14 : DayOf2016(14) {
  private val MATCH_3 = "(.)(\\1)(\\1)".toRegex()

  override fun first(): Any? {
    val cache = mutableMapOf<Int, String>()
    val hash = { n: Int ->
      cache.getOrPut(n) {
        "$data$n".md5()
      }
    }

    return solve(64, hash)
  }

  override fun second(): Any? {
    val cache = mutableMapOf<Int, String>()
    val hash = { n: Int ->
      cache.getOrPut(n) {
        (0..2016).fold("$data$n") { acc, _ ->
          acc.md5()
        }
      }
    }

    return solve(64, hash)
  }

  private fun solve(
    count: Int,
    hash: (Int) -> String
  ): Int {
    return generateSequence(0) { it + 1 }
      .map { it to hash(it) }
      .filter { MATCH_3.containsMatchIn(it.second) }
      .filter { (i, hashed) ->
        val (match) = checkNotNull(MATCH_3.find(hashed)).destructured
        val next = match.repeat(5)
        (i + 1..i + 1000).any { value -> next in hash(value) }
      }
      .drop(count - 1)
      .first()
      .first
  }
}

fun main() = SomeDay.mainify(Day14)
