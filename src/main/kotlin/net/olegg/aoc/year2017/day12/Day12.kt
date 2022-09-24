package net.olegg.aoc.year2017.day12

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 12](https://adventofcode.com/2017/day/12)
 */
object Day12 : DayOf2017(12) {
  override fun first(data: String): Any? {
    val nodes = data.trimIndent()
      .lines()
      .map { it.replace("[<\\->,]".toRegex(), "") }
      .map { it.parseInts() }
      .associate { it[0] to it.subList(1, it.size).toSet() }

    val visited = mutableSetOf(0)
    val queue = ArrayDeque(listOf(0))
    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      val toVisit = nodes[curr].orEmpty() - visited
      visited += toVisit
      queue += toVisit
    }

    return visited.size
  }

  override fun second(data: String): Any? {
    val nodes = data.trimIndent()
      .lines()
      .map { it.replace("[<\\->,]".toRegex(), "") }
      .map { it.parseInts() }
      .map { it[0] to it.subList(1, it.size).toSet() }
      .toMap()
      .toMutableMap()

    var components = 0
    while (nodes.isNotEmpty()) {
      components += 1
      val node = nodes.keys.first()
      val visited = mutableSetOf(node)
      val queue = ArrayDeque(listOf(node))
      while (queue.isNotEmpty()) {
        val curr = queue.removeFirst()
        val toVisit = (nodes[curr].orEmpty()) - visited
        visited += toVisit
        queue += toVisit
      }
      visited.forEach { nodes.remove(it) }
    }

    return components
  }
}

fun main() = SomeDay.mainify(Day12)
