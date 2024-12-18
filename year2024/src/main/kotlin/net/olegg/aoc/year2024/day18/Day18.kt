package net.olegg.aoc.year2024.day18

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.NEXT_4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 18](https://adventofcode.com/2024/day/18)
 */
object Day18 : DayOf2024(18) {
  private val size = 71

  override fun first(): Any? {
    val bytes = lines.map { line ->
      val (x, y) = line.parseInts(",")
      Vector2D(x, y)
    }.take(1024).toSet()

    val map = List(size) { y -> List(size) { x -> Vector2D(x, y) in bytes } }

    val start = Vector2D(0, 0) to 0
    val finish = Vector2D(size - 1, size - 1)
    val queue = ArrayDeque(listOf(start))
    val seen = mutableSetOf<Pair<Vector2D, Int>>()

    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      val (point, step) = curr
      if (point == finish) {
        return step
      }
      if (curr in seen) {
        continue
      }
      seen += curr

      queue += NEXT_4.map { point + it.step }
        .filter { map[it] == false }
        .map { it to step + 1 }
    }

    return -1
  }
}

fun main() = SomeDay.mainify(Day18)
