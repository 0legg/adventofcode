package net.olegg.aoc.year2016.day20

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2016.DayOf2016
import java.util.TreeSet

/**
 * See [Year 2016, Day 20](https://adventofcode.com/2016/day/20)
 */
object Day20 : DayOf2016(20) {
  private val regex = "(\\d+)-(\\d+)".toRegex()

  override fun first(): Any? {
    val banned = createBlacklist(data)

    return if (banned.first().first > 0) 0 else banned.first().last + 1
  }

  override fun second(): Any? {
    val banned = createBlacklist(data)

    return banned.fold((-1L..-1L) to 0L) { acc, range ->
      range to acc.second + (range.first - acc.first.last - 1)
    }.second +
      ((1L shl 32) - banned.last().last - 1)
  }

  private fun createBlacklist(data: String): TreeSet<LongRange> {
    val banned = TreeSet<LongRange>(compareBy({ it.first }, { it.last }))
    data
      .trim()
      .lines()
      .filter { it.isNotBlank() }
      .mapNotNull { line -> regex.find(line)?.groupValues?.let { it[1].toLong()..it[2].toLong() } }
      .forEach { mask ->
        val join = banned.filter { mask.overlaps(it) } + listOf(mask)
        banned.removeAll(join.toSet())
        val toBan = join.fold(LongRange.EMPTY) { acc, next ->
          if (acc.isEmpty()) {
            next
          } else {
            minOf(acc.first, next.first)..maxOf(acc.last, next.last)
          }
        }
        banned.add(toBan)
      }
    return banned
  }

  private fun LongRange.extend(value: Long) = LongRange(start - value, endInclusive + value)

  private fun LongRange.overlaps(other: LongRange): Boolean {
    return with(extend(1)) {
      other.first in this || other.last in this
    } || with(other.extend(1)) {
      this@overlaps.first in this || this@overlaps.last in this
    }
  }
}

fun main() = SomeDay.mainify(Day20)
