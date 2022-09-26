package net.olegg.aoc.year2018.day20

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.Neighbors4
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 20](https://adventofcode.com/2018/day/20)
 */
object Day20 : DayOf2018(20) {
  private val START = Vector2D()
  private val MOVES = mapOf(
    'W' to L,
    'E' to R,
    'N' to U,
    'S' to D
  )

  override fun first(): Any? {
    val route = data.trim('^', '$', ' ', '\n')

    return visitAll(route).values.max()
  }

  override fun second(): Any? {
    val route = data.trim('^', '$', ' ', '\n')

    return visitAll(route).count { it.value >= 1000 }
  }

  private fun visitAll(route: String): Map<Vector2D, Int> {
    val stack = ArrayDeque(listOf(START))
    val edges = mutableSetOf<Pair<Vector2D, Vector2D>>()
    val verts = mutableSetOf<Vector2D>()

    route.fold(START) { curr, char ->
      val next = when (char) {
        in MOVES -> (curr + (MOVES[char]?.step ?: Vector2D()))
        ')' -> stack.removeLast()
        '|' -> stack.last()
        '(' -> curr.also { stack.addLast(curr) }
        else -> curr
      }
      edges += curr to next
      edges += next to curr
      verts += next
      return@fold next
    }

    val queue = ArrayDeque(listOf(START))
    val visited = mutableMapOf(START to 0).withDefault { 0 }

    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      val dist = visited.getValue(curr) + 1
      val nexts = Neighbors4
        .map { curr + it.step }
        .filter { it in verts }
        .filter { it !in visited }
        .filter { curr to it in edges }

      queue += nexts
      visited += nexts.map { it to dist }
    }

    return visited
  }
}

fun main() = SomeDay.mainify(Day20)
