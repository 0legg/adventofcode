package net.olegg.adventofcode.year2016.day9

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2016.DayOf2016

/**
 * @see <a href="http://adventofcode.com/2016/day/9">Year 2016, Day 9</a>
 */
class Day9 : DayOf2016(9) {
  companion object {
    private val PATTERN = "\\((\\d+)x(\\d+)\\)".toRegex()
  }

  override fun first(data: String): Any? {
    val input = data.replace("\\s".toRegex(), "")
    return measure(input, false)
  }

  override fun second(data: String): Any? {
    val input = data.replace("\\s".toRegex(), "")
    return measure(input, true)
  }

  private fun measure(data: String, unfold: Boolean): Long {
    return PATTERN.find(data)?.let { match ->
      val (length, repeats) = match.groupValues.drop(1).let { it.first().toInt() to it.last().toLong() }
      val (matchStart, matchEnd) = (match.range.first to match.range.last)
      return@let if (unfold) {
        matchStart +
            repeats * measure(data.substring(matchEnd + 1, matchEnd + 1 + length), true) +
            measure(data.substring(matchEnd + 1 + length), true)
      } else {
        matchStart +
            repeats * length +
            measure(data.substring(matchEnd + 1 + length), false)
      }
    } ?: data.length.toLong()
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day9::class)
