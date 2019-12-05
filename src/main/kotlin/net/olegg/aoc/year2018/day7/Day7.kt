package net.olegg.aoc.year2018.day7

import kotlin.math.max
import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2018.DayOf2018

/**
 * See [Year 2018, Day 7](https://adventofcode.com/2018/day/7)
 */
object Day7 : DayOf2018(7) {
  private val PATTERN = "Step (\\w) must be finished before step (\\w) can begin\\.".toRegex()

  override fun first(data: String): Any? {
    val edges = data
        .trim()
        .lines()
        .mapNotNull { line ->
          PATTERN.matchEntire(line)?.let { match ->
            val (a, b) = match.destructured
            return@mapNotNull a to b
          }
        }

    val vertices = edges.flatMap { it.toList() }.toMutableSet()

    val neighbors = edges
        .groupBy { it.first }
        .mapValues { neighbors -> neighbors.value.map { it.second }.toSet() }
        .toMutableMap()

    val answer = mutableListOf<String>()
    while (vertices.isNotEmpty()) {
      val next = vertices
          .filter { v ->
            neighbors.none { v in it.value }
          }
          .sorted()
          .first()
      answer += next
      vertices.remove(next)
      neighbors.remove(next)
    }

    return answer.joinToString(separator = "")
  }

  override fun second(data: String): Any? {
    val edges = data
        .trim()
        .lines()
        .mapNotNull { line ->
          PATTERN.matchEntire(line)?.let { match ->
            val (a, b) = match.destructured
            return@mapNotNull a to b
          }
        }

    val vertices = edges.flatMap { it.toList() }.toMutableSet()

    val neighbors = edges
        .groupBy { it.first }
        .mapValues { neighbors -> neighbors.value.map { it.second }.toSet() }
        .toMutableMap()

    val start = vertices
        .map { it to 0 }
        .toMap()
        .toMutableMap()

    val workers = IntArray(5) { 0 }
    while (vertices.isNotEmpty()) {
      val available = vertices
          .filter { v ->
            neighbors.none { v in it.value }
          }
      val soonest = start
          .filter { it.key in available }
          .map { it.value }
          .min() ?: 0

      val next = available
          .sorted()
          .first { start[it] == soonest }

      val worker = workers.indexOfFirst { it == workers.min() }
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
