package net.olegg.aoc.year2024.day18

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.NEXT_4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.set
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

  override fun second(): Any? {
    val bytes = lines.map { line ->
      val (x, y) = line.parseInts(",")
      Vector2D(x, y)
    }

    var left = 0
    var right = bytes.lastIndex
    var mid = (left + right) / 2

    while (right - left > 1) {
      val cut = bytes.take(mid).toSet()
      val map = List(size) { y -> MutableList(size) { x -> Vector2D(x, y) in cut } }

      if (fill(map)) {
        left = mid
      } else {
        right = mid
      }
      mid = (left + right) / 2
    }

    return bytes[left].let { "${it.x},${it.y}" }
  }

  private fun fill(map: List<MutableList<Boolean>>): Boolean {
    val start = Vector2D(0, 0)
    val finish = Vector2D(size - 1, size - 1)
    val queue = ArrayDeque(listOf(start))
    val seen = mutableSetOf<Vector2D>()

    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      if (curr == finish) {
        return true
      }
      if (curr in seen) {
        continue
      }
      seen += curr

      val next = NEXT_4.map { curr + it.step }
        .filter { map[it] == false }

      next.forEach { map[it] = true }

      queue += next
    }

    return map[finish]!!
  }
}

fun main() = SomeDay.mainify(Day18)
