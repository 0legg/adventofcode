package net.olegg.aoc.year2024.day5

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.pairs
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 5](https://adventofcode.com/2024/day/5)
 */
object Day5 : DayOf2024(5) {
  override fun first(): Any? {
    val (rulesRaw, updatesRaw) = data.split("\n\n")

    val rules = rulesRaw
      .lines()
      .map { it.parseInts("|").toPair() }
      .toSet()

    val updates = updatesRaw
      .lines()
      .map { it.parseInts(",") }

    return updates
      .filter { update ->
        update.pairs().none { (it.second to it.first) in rules }
      }
      .sumOf { it[it.size / 2] }
  }

  override fun second(): Any? {
    val (rulesRaw, updatesRaw) = data.split("\n\n")

    val rules = rulesRaw
      .lines()
      .map { it.parseInts("|").toPair() }
      .toSet()

    val updates = updatesRaw
      .lines()
      .map { it.parseInts(",") }

    val broken = updates
      .filter { update ->
        update.pairs().any { (it.second to it.first) in rules }
      }

    return broken
      .map { original ->
        val edges = rules.filter { it.first in original && it.second in original }
        val order = topologicalSort(edges).withIndex().associate { it.value to it.index }
        original.sortedBy { order[it] }
      }
      .sumOf { it[it.size / 2] }
  }

  private fun topologicalSort(edges: List<Pair<Int, Int>>): List<Int> {
    val noIncoming = ArrayDeque<Int>()
    val result = ArrayDeque<Int>()

    val neighbours = edges.groupingBy { it.first }.fold(emptyList<Int>()) { acc, value -> acc + value.second }
    val incoming = edges.groupingBy { it.second }.eachCount().toMutableMap()
    edges.flatMap { it.toList() }.forEach { incoming.putIfAbsent(it, 0) }
    noIncoming.addAll(incoming.filter { it.value == 0 }.keys)

    while (noIncoming.isNotEmpty()) {
      val curr = noIncoming.removeFirst()
      result.add(curr)

      neighbours[curr].orEmpty().forEach { next ->
        val new = incoming.getValue(next) - 1
        incoming[next] = new
        if (new == 0) {
          noIncoming.add(next)
        }
      }
    }

    return result.toList()
  }
}

fun main() = SomeDay.mainify(Day5)
