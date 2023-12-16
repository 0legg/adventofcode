package net.olegg.aoc.year2023.day16

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.fit
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.set
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 16](https://adventofcode.com/2023/day/16)
 */
object Day16 : DayOf2023(16) {
  override fun first(): Any? {
    val energized = matrix.map { it.map { 0 }.toMutableList() }

    val queue = ArrayDeque(listOf(Vector2D() to R))
    val seen = mutableSetOf<Pair<Vector2D, Directions>>()

    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      if (curr in seen) continue
      seen += curr
      val (pos, dir) = curr
      if (!energized.fit(pos)) continue
      energized[pos] = 1
      
      when (matrix[pos]) {
        '.' -> queue.add(pos + dir.step to dir)
        '|' -> when (dir) {
          L, R -> {
            queue.add(pos + U.step to U)
            queue.add(pos + D.step to D)
          }
          U, D -> queue.add(pos + dir.step to dir)
          else -> Unit
        }
        '-' -> when (dir) {
          U, D -> {
            queue.add(pos + L.step to L)
            queue.add(pos + R.step to R)
          }
          L, R -> queue.add(pos + dir.step to dir)
          else -> Unit
        }
        '/' -> when (dir) {
          L -> queue.add(pos + D.step to D)
          R -> queue.add(pos + U.step to U)
          D -> queue.add(pos + L.step to L)
          U -> queue.add(pos + R.step to R)
          else -> Unit
        }
        '\\' -> when (dir) {
          L -> queue.add(pos + U.step to U)
          R -> queue.add(pos + D.step to D)
          D -> queue.add(pos + R.step to R)
          U -> queue.add(pos + L.step to L)
          else -> Unit
        }
      }
    }

    return energized.sumOf { it.sum() }
  }
}

fun main() = SomeDay.mainify(Day16)
