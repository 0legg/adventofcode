package net.olegg.aoc.year2018.day20

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018
import java.util.ArrayDeque

/**
 * See [Year 2018, Day 20](https://adventofcode.com/2018/day/20)
 */
object Day20 : DayOf2018(20) {
  private val START = 0 to 0
  private val MOVES = mapOf(
      'W' to (-1 to 0),
      'E' to (1 to 0),
      'N' to (0 to -1),
      'S' to (0 to 1)
  ).withDefault { 0 to 0 }

  override fun first(data: String): Any? {
    val route = data.trim('^', '$', ' ', '\n')

    return visitAll(route).values.max()
  }

  override fun second(data: String): Any? {
    val route = data.trim('^', '$', ' ', '\n')

    return visitAll(route).count { it.value >= 1000 }
  }

  private fun visitAll(route: String): Map<Pair<Int, Int>, Int> {
    val stack = ArrayDeque(listOf(START))
    val edges = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
    val verts = mutableSetOf<Pair<Int, Int>>()

    route.fold(START) { curr, char ->
      val (x, y) = curr
      val next = when(char) {
        in MOVES -> (x + MOVES.getValue(char).first) to (y + MOVES.getValue(char).second)
        ')' -> stack.pop()
        '|' -> stack.peek()
        '(' -> curr.also { stack.push(curr) }
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
      val curr = queue.pop()
      val (x, y) = curr
      val dist = visited.getValue(curr) + 1
      val nexts = MOVES.values
          .map { (dx, dy) -> (x + dx) to (y + dy) }
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
