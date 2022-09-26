package net.olegg.aoc.year2018.day5

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 5](https://adventofcode.com/2018/day/5)
 */
object Day5 : DayOf2018(5) {
  override fun first(): Any? {
    val bad = ('a'..'z').map { a -> "$a${a.uppercase()}" } +
      ('A'..'Z').map { a -> "$a${a.lowercase()}" }
    var curr = data
    do {
      val prev = curr
      curr = bad.fold(curr) { acc, token -> acc.replace(token, "") }
    } while (prev != curr)

    return curr.length
  }

  override fun second(): Any? {
    val bad = ('a'..'z').map { a -> "$a${a.uppercase()}" } +
      ('A'..'Z').map { a -> "$a${a.lowercase()}" }

    val source = data
    val filtered = ('a'..'z').map { a -> source.replace("$a", "").replace(a.uppercase(), "") }

    return filtered
      .minOf { polymer ->
        var curr = polymer
        do {
          val prev = curr
          curr = bad.fold(curr) { acc, token -> acc.replace(token, "") }
        } while (prev != curr)
        return@minOf curr.length
      }
  }
}

fun main() = SomeDay.mainify(Day5)
