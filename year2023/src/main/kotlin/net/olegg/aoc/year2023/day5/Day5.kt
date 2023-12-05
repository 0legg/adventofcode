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
          excludes = emptyList(),
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

  override fun second(): Any? {
    val sections = data.split("\n\n")
    val seeds = sections.first()
      .parseLongs(" ")
      .chunked(2)
      .map { it.first()..<it.first() + it.last() }

    val startSeeds = seeds.normalize()

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
          excludes = emptyList(),
        )
      }

    val max = maxOf(
      startSeeds.maxOf { it.last },
      maps.maxOf { map -> map.ranges.maxOf { maxOf(it.from.last, it.to.last) } },
    )

    val full = 0..max

    val fullMaps = maps.map { sourceMap ->
      sourceMap.copy(
        excludes = xored(full, sourceMap.ranges.map { it.from }).map { longRange ->
          Range(
            from = longRange,
            to = longRange,
            delta = 0,
          )
        },
      )
    }

    return fullMaps
      .fold(startSeeds) { curr, mapping ->
        curr.flatMap { seedRange ->
          mapping.ranges
            .filter { it.from touch seedRange }
            .map { it to (it.from overlap seedRange) }
            .map { (range, seedRange) -> seedRange.first + range.delta..seedRange.last + range.delta } +

            mapping.excludes
              .filter { it.from touch seedRange }
              .map { it.from overlap seedRange }
        }.normalize()
      }
      .minOf { it.first }
  }

  private fun List<LongRange>.normalize(): List<LongRange> {
    val events = this.flatMap {
      listOf(it.first to 1, it.last + 1 to -1)
    }.sortedWith(compareBy({ it.first }, { -it.second }))

    return buildList {
      var depth = 0
      var from = Long.MIN_VALUE
      events.forEach { (place, delta) ->
        if (depth == 0 && delta == 1) {
          from = place
        }
        depth += delta
        if (depth == 0) {
          add(from..place)
        }
      }
    }
  }

  private fun xored(
    fullRange: LongRange,
    exclude: List<LongRange>
  ): List<LongRange> {
    val excludeNorm = exclude.normalize()
    return buildList {
      add(fullRange.first)
      excludeNorm.forEach {
        add(it.first - 1)
        add(it.last + 1)
      }
      add(fullRange.last)
    }
      .chunked(2)
      .mapNotNull {
        if (it.first() <= it.last()) {
          it.first()..it.last()
        } else {
          null
        }
      }
  }

  private data class Mapping(
    val from: String,
    val to: String,
    val ranges: List<Range>,
    val excludes: List<Range>,
  )

  private data class Range(
    val from: LongRange,
    val to: LongRange,
    val delta: Long,
  )

  private infix fun LongRange.touch(other: LongRange) =
    first in other || last in other || other.first in this || other.last in this

  private infix fun LongRange.overlap(other: LongRange) = LongRange(maxOf(first, other.first), minOf(last, other.last))
}

fun main() = SomeDay.mainify(Day5)
