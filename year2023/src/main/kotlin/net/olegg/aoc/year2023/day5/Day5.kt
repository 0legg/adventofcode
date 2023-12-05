package net.olegg.aoc.year2023.day5

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.utils.toTriple
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 5](https://adventofcode.com/2023/day/5)
 */
object Day5 : DayOf2023(5) {

  override fun first(): Any? {
    val sections = data.split("\n\n")
    val seeds = sections.first().parseLongs(" ")

    val maps = sections.drop(1)
      .map { section ->
        val lines = section.lines()
        val (from, to) = lines.first().substringBefore(" ").split("-to-")
        val ranges = lines.drop(1)
          .map { it.parseLongs(" ").toTriple() }
          .map { (to, from, length) ->
            Range(
              from = from..<from + length,
              to = to..<to + length,
              delta = to - from,
            )
          }

        Mapping(
          from = from,
          to = to,
          ranges = ranges,
        )
      }

    return maps
      .fold(seeds) { curr, mapping ->
        curr.map { seed ->
          seed + (mapping.ranges.firstOrNull { seed in it.from }?.delta ?: 0)
        }
      }
      .min()
  }

  private data class Mapping(
    val from: String,
    val to: String,
    val ranges: List<Range>,
  )

  private data class Range(
    val from: LongRange,
    val to: LongRange,
    val delta: Long,
  )
}

fun main() = SomeDay.mainify(Day5)
