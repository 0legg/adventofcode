package net.olegg.aoc.year2018.day5

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 5](https://adventofcode.com/2018/day/5)
 */
class Day5 : DayOf2018(5) {
  override fun first(data: String): Any? {
    val bad = ('a'..'z').map { a -> "$a${a.toUpperCase()}" } +
        ('A'..'Z').map { a -> "$a${a.toLowerCase()}" }
    var curr = data.trim()
    do {
      val prev = curr
      curr = bad.fold(curr) { acc, token -> acc.replace(token, "") }
    } while (prev != curr)

    return curr.length
  }

  override fun second(data: String): Any? {
    val bad = ('a'..'z').map { a -> "$a${a.toUpperCase()}" } +
        ('A'..'Z').map { a -> "$a${a.toLowerCase()}" }

    val source = data.trim()
    val filtered = ('a'..'z').map { a -> source.replace("$a", "").replace("${a.toUpperCase()}", "") }

    return filtered
        .map { polymer ->
          var curr = polymer
          do {
            val prev = curr
            curr = bad.fold(curr) { acc, token -> acc.replace(token, "") }
          } while (prev != curr)
          return@map curr.length
        }
        .min()
  }
}

fun main() = SomeDay.mainify(Day5::class)
