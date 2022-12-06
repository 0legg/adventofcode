package net.olegg.aoc.year2017.day20

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector3D
import net.olegg.aoc.utils.parseInts
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
      .map { it.parseInts(",") }
      .map { nums ->
        Triple(
          Vector3D(nums[0], nums[1], nums[2]),
          Vector3D(nums[3], nums[4], nums[5]),
          Vector3D(nums[6], nums[7], nums[8]),
        )
      }

    return (0..1_000)
      .fold(points) { acc, _ ->
        acc.map { (position, speed, acceleration) ->
          val newSpeed = speed + acceleration
          val newPosition = position + newSpeed
          return@map Triple(newPosition, newSpeed, acceleration)
        }
      }
      .withIndex()
      .minBy { it.value.first.manhattan() }
      .index
  }

  override fun second(): Any? {
    val points = lines
      .map { line -> line.replace("[pva=<> ]".toRegex(), "") }
      .map { it.parseInts(",") }
      .mapIndexed { index, nums ->
        index to Triple(
          Vector3D(nums[0], nums[1], nums[2]),
          Vector3D(nums[3], nums[4], nums[5]),
          Vector3D(nums[6], nums[7], nums[8]),
        )
      }

    return (0..1_000)
      .fold(points) { acc, _ ->
        acc.map { (index, values) ->
          val (position, speed, acceleration) = values
          val newSpeed = speed + acceleration
          val newPosition = position + newSpeed
          return@map index to Triple(newPosition, newSpeed, acceleration)
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
