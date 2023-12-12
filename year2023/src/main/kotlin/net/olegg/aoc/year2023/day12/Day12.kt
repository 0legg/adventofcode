package net.olegg.aoc.year2023.day12

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 12](https://adventofcode.com/2023/day/12)
 */
object Day12 : DayOf2023(12) {
  private val EMPTY = "\\s+".toRegex()

  override fun first(): Any? {
    return lines
      .map { line -> line.split(" ").toPair() }
      .map { (line, counts) ->
        line.replace('.', ' ') to counts.parseInts(",")
      }
      .sumOf { (line, counts) ->
        val unknown = line.mapIndexedNotNull { index, c -> index.takeIf { c == '?' } }
        val replaces = unknown.size

        (0..<(1 shl replaces)).count { mask ->
          val withMask = unknown.foldIndexed(line.toCharArray()) { index, acc, position ->
            acc.apply {
              this[position] = if (((mask shr index) and 1) == 1) '#' else ' '
            }
          }.let(::String)

          withMask
            .trim()
            .split(EMPTY)
            .map { it.length } == counts
        }
      }
  }
}

fun main() = SomeDay.mainify(Day12)
