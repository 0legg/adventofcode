package net.olegg.aoc.year2016.day14

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.md5
import net.olegg.aoc.year2016.DayOf2016
import org.funktionale.memoization.memoize

/**
 * See [Year 2016, Day 14](https://adventofcode.com/2016/day/14)
 */
object Day14 : DayOf2016(14) {
  private val MATCH_3 = "(.)(\\1)(\\1)".toRegex()

  override fun first(data: String): Any? {
    val hash = { n: Int ->
      "$data$n".md5()
    }.memoize()

    return solve(64, hash)
  }

  override fun second(data: String): Any? {
    val hash = { n: Int ->
      (0..2016).fold("$data$n") { acc, _ ->
        acc.md5()
      }
    }.memoize()

    return solve(64, hash)
  }

  fun solve(count: Int, hash: (Int) -> String): Int {
    return sequence {
      var i = 0
      while (true) {
        val curr = hash(i)
        MATCH_3.find(curr)?.let { match ->
          val next = match.groupValues[1].repeat(5)
          if ((i + 1..i + 1000).any { value -> hash(value).contains(next) }) {
            yield(i)
          }
        }
        i++
      }
    }
        .take(count)
        .last()
  }
}

fun main() = SomeDay.mainify(Day14)
