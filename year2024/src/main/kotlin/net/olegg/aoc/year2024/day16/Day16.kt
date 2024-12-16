package net.olegg.aoc.year2024.day16

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Directions.Companion.CCW
import net.olegg.aoc.utils.Directions.Companion.CW
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.find
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2024.DayOf2024
import java.util.PriorityQueue

/**
 * See [Year 2024, Day 16](https://adventofcode.com/2024/day/16)
 */
object Day16 : DayOf2024(16) {
  private val empty = setOf('.', 'S', 'E')
  override fun first(): Any? {
    val start = matrix.find('S')!!
    val end = matrix.find('E')!!

    val queue = PriorityQueue<Triple<Vector2D, Directions, Int>>(compareBy { it.third })
    queue.add(Triple(start, Directions.R, 0))
    val seen = mutableSetOf<Pair<Vector2D, Directions>>()

    while (queue.isNotEmpty()) {
      val point = queue.remove()
      val (curr, dir, length) = point
      if (curr == end) {
        return length
      }
      val config = curr to dir
      if (config in seen) {
        continue
      }
      seen += config

      if (matrix[curr + dir.step] in empty) {
        queue.add(Triple(curr + dir.step, dir, length + 1))
      }
      queue.add(Triple(curr, CW[dir]!!, length + 1000))
      queue.add(Triple(curr, CCW[dir]!!, length + 1000))
    }

    return -1
  }
}

fun main() = SomeDay.mainify(Day16)
