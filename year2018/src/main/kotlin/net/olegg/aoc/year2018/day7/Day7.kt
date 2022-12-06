package net.olegg.aoc.year2018.day7

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018
import kotlin.math.max

/**
 * See [Year 2018, Day 7](https://adventofcode.com/2018/day/7)
 */
object Day7 : DayOf2018(7) {
  private val PATTERN = "Step (\\w) must be finished before step (\\w) can begin\\.".toRegex()

  override fun first(): Any? {
    val edges = lines
      .mapNotNull { line ->
        PATTERN.matchEntire(line)?.let { match ->
          val (a, b) = match.destructured
          return@mapNotNull a to b
        }
      }

    val vertices = edges.flatMap { it.toList() }.toMutableSet()

    val neighbors = edges
      .groupBy { it.first }
      .mapValuesTo(mutableMapOf()) { neighbors ->
        neighbors.value.map { it.second }.toSet()
      }

    val answer = mutableListOf<String>()
    while (vertices.isNotEmpty()) {
      val next = vertices
        .filter { v ->
          neighbors.none { v in it.value }
        }
        .minOf { it }
      answer += next
      vertices.remove(next)
      neighbors.remove(next)
    }

    return answer.joinToString(separator = "")
  }

  override fun second(): Any? {
    val edges = lines
      .mapNotNull { line ->
        PATTERN.matchEntire(line)?.let { match ->
          val (a, b) = match.destructured
          return@mapNotNull a to b
        }
      }

    val vertices = edges.flatMap { it.toList() }.toMutableSet()

    val neighbors = edges
      .groupBy { it.first }
      .mapValuesTo(mutableMapOf()) { neighbors ->
        neighbors.value.map { it.second }.toSet()
      }

    val start = vertices.associateWithTo(mutableMapOf()) { 0 }

    val workers = IntArray(5) { 0 }
    while (vertices.isNotEmpty()) {
      val available = vertices
        .filter { v ->
          neighbors.none { v in it.value }
        }
      val soonest = start
        .filter { it.key in available }
        .minOfOrNull { it.value } ?: 0

      val next = available
        .sorted()
        .first { start[it] == soonest }

      val minWorker = workers.min()
      val worker = workers.indexOfFirst { it == minWorker }
      val time = max(workers[worker], soonest) + 61 + (next[0] - 'A')
      workers[worker] = time

      vertices.remove(next)
      neighbors
        .remove(next)
        .orEmpty()
        .filter { it in vertices }
        .forEach { v ->
          start[v] = max(start[v] ?: 0, time)
        }
    }

    return workers.max()
  }
}

fun main() = SomeDay.mainify(Day7)
