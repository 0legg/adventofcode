package net.olegg.aoc.year2024.day20

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.NEXT_4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.find
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.pairs
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 20](https://adventofcode.com/2024/day/20)
 */
object Day20 : DayOf2024(20) {
  private val cheats = NEXT_4.map { it.step * 2 }

  override fun first(): Any? {
    val start = matrix.find('S')!!
    val end = matrix.find('E')!!

    val queue = ArrayDeque(listOf(start to 0))
    val seen = mutableMapOf<Vector2D, Int>()

    while (queue.isNotEmpty()) {
      val (curr, step) = queue.removeFirst()

      if (curr in seen) {
        continue
      }
      seen[curr] = step
      if (curr == end) {
        break
      }

      queue += NEXT_4.map { curr + it.step }
        .filter { it == end || matrix[it] == '.'}
        .filter { it !in seen }
        .map { it to step + 1 }
    }

    val path = generateSequence(end to seen[end]!!) { (curr, step) ->
      NEXT_4.map { curr + it.step }
        .firstOrNull { seen[it] == step - 1 }
        ?.let { it to step - 1 }
    }.toList().reversed()

    return path.pairs().count { (first, second) ->
      val (firstPos, firstStep) = first
      val (secondPos, secondStep) = second

      secondStep - firstStep - 2 >= 100 && (secondPos - firstPos) in cheats
    }
  }
}

fun main() = SomeDay.mainify(Day20)
