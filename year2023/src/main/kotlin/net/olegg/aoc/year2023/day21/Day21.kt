package net.olegg.aoc.year2023.day21

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.find
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 21](https://adventofcode.com/2023/day/21)
 */
object Day21 : DayOf2023(21) {
  override fun first(): Any? {
    val start = matrix.find('S')!!

    val toVisit = mutableMapOf(0 to mutableSetOf(start))

    (0..<64).forEach { step ->
      val nodes = ArrayDeque(toVisit.getValue(step))
      val next = mutableSetOf<Vector2D>()
      while (nodes.isNotEmpty()) {
        val curr = nodes.removeFirst()
        next += Directions.NEXT_4
          .map { curr + it.step }
          .filter { matrix[it] == '.' || matrix[it] == 'S' }
      }
      toVisit[step + 1] = next
    }

    return toVisit[64]!!.size
  }
}

fun main() = SomeDay.mainify(Day21)
