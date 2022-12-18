package net.olegg.aoc.year2022.day18

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector3D
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 18](https://adventofcode.com/2022/day/18)
 */
object Day18 : DayOf2022(18) {
  private val dirs = listOf(
    Vector3D(1, 0, 0),
    Vector3D(-1, 0, 0),
    Vector3D(0, 1, 0),
    Vector3D(0, -1, 0),
    Vector3D(0, 0, 1),
    Vector3D(0, 0, -1),
  )

  override fun first(): Any? {
    val blocks = lines
      .map { it.parseInts(",") }
      .map { Vector3D(it[0], it[1], it[2]) }
      .toSet()

    return blocks.sumOf { block ->
      dirs.count { (block + it) !in blocks }
    }
  }

  override fun second(): Any? {
    val blocks = lines
      .map { it.parseInts(",") }
      .map { Vector3D(it[0], it[1], it[2]) }
      .toSet()

    val minBlock = Vector3D(
      x = blocks.minOf { it.x } - 1,
      y = blocks.minOf { it.y } - 1,
      z = blocks.minOf { it.z } - 1,
    )

    val maxBlock = Vector3D(
      x = blocks.maxOf { it.x } + 1,
      y = blocks.maxOf { it.y } + 1,
      z = blocks.maxOf { it.z } + 1,
    )

    val filled = mutableSetOf<Vector3D>()
    val queue = ArrayDeque(listOf(minBlock))

    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      if (curr in filled) {
        continue
      }
      filled += curr
      queue += dirs
        .map { it + curr }
        .filter { it.x >= minBlock.x && it.y >= minBlock.y && it.z >= minBlock.z }
        .filter { it.x <= maxBlock.x && it.y <= maxBlock.y && it.z <= maxBlock.z }
        .filter { it !in filled }
        .filter { it !in blocks }
    }

    return blocks.sumOf { block ->
      dirs.count { (block + it) in filled }
    }
  }
}

fun main() = SomeDay.mainify(Day18)
