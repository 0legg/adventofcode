package net.olegg.aoc.year2017.day20

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.year2017.DayOf2017
import kotlin.math.abs

/**
 * See [Year 2017, Day 20](https://adventofcode.com/2017/day/20)
 */
object Day20 : DayOf2017(20) {
  override fun first(): Any? {
    val points = lines
      .map { line -> line.replace("[pva=<> ]".toRegex(), "") }
      .map { it.parseLongs(",") }
      .map { nums ->
        Triple(
          Triple(nums[0], nums[1], nums[2]),
          Triple(nums[3], nums[4], nums[5]),
          Triple(nums[6], nums[7], nums[8])
        )
      }

    return (0..1_000)
      .fold(points) { acc, _ ->
        acc.map { prev ->
          val speed = Triple(
            prev.second.first + prev.third.first,
            prev.second.second + prev.third.second,
            prev.second.third + prev.third.third
          )
          val point = Triple(
            prev.first.first + speed.first,
            prev.first.second + speed.second,
            prev.first.third + speed.third
          )
          return@map prev.copy(first = point, second = speed)
        }
      }
      .mapIndexed { index, triple -> index to triple }
      .minByOrNull { abs(it.second.first.first) + abs(it.second.first.second) + abs(it.second.first.third) }
      ?.first
  }

  override fun second(): Any? {
    val points = lines
      .map { line -> line.replace("[pva=<> ]".toRegex(), "") }
      .map { it.parseLongs(",") }
      .mapIndexed { index, nums ->
        index to Triple(
          Triple(nums[0], nums[1], nums[2]),
          Triple(nums[3], nums[4], nums[5]),
          Triple(nums[6], nums[7], nums[8])
        )
      }

    return (0..1_000)
      .fold(points) { acc, _ ->
        acc.map { (index, prev) ->
          val speed = Triple(
            prev.second.first + prev.third.first,
            prev.second.second + prev.third.second,
            prev.second.third + prev.third.third
          )
          val point = Triple(
            prev.first.first + speed.first,
            prev.first.second + speed.second,
            prev.first.third + speed.third
          )
          return@map index to prev.copy(first = point, second = speed)
        }
          .groupBy { it.second.first }
          .filterValues { it.size == 1 }
          .values
          .flatten()
      }
      .count()
  }
}

fun main() = SomeDay.mainify(Day20)
