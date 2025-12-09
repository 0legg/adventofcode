package net.olegg.aoc.year2025.day9

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.NEXT_4
import net.olegg.aoc.utils.Directions.Companion.NEXT_8
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.pairs
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.parseLongs
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2025.DayOf2025
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * See [Year 2025, Day 9](https://adventofcode.com/2025/day/9)
 */
object Day9 : DayOf2025(9) {
  override fun first(): Any? {
    return lines
      .map { it.parseLongs(",").toPair() }
      .pairs()
      .maxOf { (a, b) ->
        (abs(a.first - b.first) + 1) * (abs(a.second - b.second) + 1)
      }
  }

  override fun second(): Any? {
    val vertices = lines
      .map { it.parseInts(",") }
      .map { Vector2D(it[0], it[1]) }

    val edges = (vertices + vertices.first())
      .zipWithNext()
      .flatMap { (a, b) ->
        val dir = (b - a).dir()
        generateSequence(a) { it + dir }.takeWhile { it != b }
      }
      .toSet()

    val fatEdges = edges
      .flatMap { edge ->
        NEXT_8.map { it.step + edge }
      }
      .toSet()

    val top = fatEdges.minOf { it.y }

    val start = fatEdges.filter { it.y == top }.minBy { it.x }

    val queue = ArrayDeque(listOf(start))
    val outside = mutableSetOf<Vector2D>()
    while (queue.isNotEmpty()) {
      val next = queue.removeFirst()
      if (!outside.add(next)) {
        continue
      }

      queue.addAll(
        NEXT_4.map { next + it.step }
          .filter { it in fatEdges }
          .filterNot { it in edges }
          .filterNot { it in outside }
      )
    }

    return vertices.pairs()
      .map { (a, b) ->
        Triple(a, b, (abs(a.x - b.x) + 1L) * (abs(a.y - b.y) + 1L))
      }
      .sortedByDescending { it.third }
      .first { (a, b, _) ->
        val (startX, endX) = min(a.x, b.x) to max(a.x, b.x)
        val (startY, endY) = min(a.y, b.y) to max(a.y, b.y)
        (startX..endX).none { x ->
          Vector2D(x, startY) in outside || Vector2D(x, endY) in outside
        } && (startY..endY).none { y ->
          Vector2D(startX, y) in outside || Vector2D(endX, y) in outside
        }
      }
      .third
  }
}

fun main() = SomeDay.mainify(Day9)
