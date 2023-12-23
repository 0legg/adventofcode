package net.olegg.aoc.year2023.day23

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Directions.Companion.NEXT_4
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

    val nodes = buildList {
      add(start)
      matrix.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
          val curr = Vector2D(x, y)
          if (c !in WALL && NEXT_4.count { matrix[curr + it.step] !in WALL } > 2) {
            add(curr)
          }
        }
      }
      add(end)
    }

    val edges = nodes.map { nodeStart ->
      buildMap(4) {
        val queue = ArrayDeque(listOf(nodeStart to setOf(nodeStart)))

        while (queue.isNotEmpty()) {
          val (curr, visited) = queue.removeFirst()

          if (curr != nodeStart && curr in nodes) {
            this[nodes.indexOf(curr)] = visited.size - 1
          } else {
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
        }
      }
    }

    var best = 0
    val seen = mutableSetOf<List<Int>>()
    val queue = PriorityQueue<List<Int>>(
      compareBy { -it.size }
    )

    queue.add(listOf(0))
    while (queue.isNotEmpty()) {
      val path = queue.remove()
      if (!seen.add(path)) {
        continue
      }

      val curr = path.last()
      if (curr == nodes.lastIndex) {
        val size = path.zipWithNext { a, b -> edges[a][b]!! }.sum()
        if (best < size) {
          best = size
        }
      }

      queue += edges[curr]
        .filter { it.key !in path }
        .map { path + it.key }
        .filter { it !in seen }
    }

    return best
  }

  override fun second(): Any? {
    val start = Vector2D(x = matrix.first().indexOf('.'), y = 0)
    val end = Vector2D(x = matrix.last().indexOf('.'), y = matrix.lastIndex)

    val nodes = buildList {
      add(start)
      matrix.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
          val curr = Vector2D(x, y)
          if (c !in WALL && NEXT_4.count { matrix[curr + it.step] !in WALL } > 2) {
            add(curr)
          }
        }
      }
      add(end)
    }

    val edges = nodes.map { nodeStart ->
      buildMap(4) {
        val queue = ArrayDeque(listOf(nodeStart to setOf(nodeStart)))

        while (queue.isNotEmpty()) {
          val (curr, visited) = queue.removeFirst()

          if (curr != nodeStart && curr in nodes) {
            this[nodes.indexOf(curr)] = visited.size - 1
          } else {
            queue += Directions.NEXT_4
              .map { curr + it.step }
              .filter { it !in visited }
              .filter { matrix[it] !in WALL }
              .map { it to visited + it }
          }
        }
      }
    }

    var best = 0
    val seen = mutableSetOf<List<Int>>()
    val queue = PriorityQueue<List<Int>>(
      compareBy { -it.size }
    )

    queue.add(listOf(0))
    while (queue.isNotEmpty()) {
      val path = queue.remove()
      if (!seen.add(path)) {
        continue
      }

      val curr = path.last()
      if (curr == nodes.lastIndex) {
        val size = path.zipWithNext { a, b -> edges[a][b]!! }.sum()
        if (best < size) {
          best = size
        }
      }

      queue += edges[curr]
        .filter { it.key !in path }
        .map { path + it.key }
        .filter { it !in seen }
    }

    return best
  }
}

fun main() = SomeDay.mainify(Day23)
