package net.olegg.aoc.year2023.day23

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2023.DayOf2023
import java.util.PriorityQueue

/**
 * See [Year 2023, Day 23](https://adventofcode.com/2023/day/23)
 */
object Day23 : DayOf2023(23) {
  private val WALL = setOf(null, '#')
  private val SLOPE = mapOf('^' to U, '<' to L, '>' to R, 'v' to D)
  override fun first(): Any? {
    val start = Vector2D(x = matrix.first().indexOf('.'), y = 0)
    val end = Vector2D(x = matrix.last().indexOf('.'), y = matrix.lastIndex)

    val best = mutableMapOf<Vector2D, Int>()
    val queue = PriorityQueue<Pair<Vector2D, Set<Vector2D>>>(
      compareBy ({ -it.second.size }, { -it.first.manhattan() })
    )

    queue.add(start to setOf(start))
    while (queue.isNotEmpty()) {
      val (curr, visited) = queue.remove()

      if (best.getOrDefault(curr, 0) >= visited.size) {
        continue
      }
      best[curr] = visited.size

      queue += Directions.NEXT_4
        .map { curr + it.step }
        .filter { it !in visited }
        .filter { matrix[it] !in WALL }
        .mapNotNull {
          when (val char = matrix[it]) {
            '.' -> it to visited + it
            in SLOPE -> {
              val slide = it + SLOPE[char]!!.step
              if (slide !in visited && matrix[slide] !in WALL) {
                slide to visited + setOf(it, slide)
              } else {
                null
              }
            }
            else -> null
          }
        }
    }

    return best.getOrDefault(end, 0) - 1
  }
}

fun main() = SomeDay.mainify(Day23)
