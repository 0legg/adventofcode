package net.olegg.aoc.year2024.day23

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.utils.transpose
import net.olegg.aoc.year2024.DayOf2024
import kotlin.math.absoluteValue

/**
 * See [Year 2024, Day 23](https://adventofcode.com/2024/day/23)
 */
object Day23 : DayOf2024(23) {
  override fun first(): Any? {
    val connections = lines.map { it.split("-").sorted() }

    return connections.flatMapIndexed { i, connI ->
      connections.drop(i + 1).flatMapIndexed { j, connJ ->
        val subset = (connI + connJ).toSet()
        if (subset.size == 3) {
          connections.drop(i + 1 + j + 1).mapNotNull { connK ->
            (subset + connK).toSet().takeIf { it.size == 3 }
          }
        } else {
          emptyList()
        }
      }
    }.count { set ->
      set.any { it.startsWith("t") }
    }
  }

  override fun second(): Any? {
    val connections = lines.map { it.split("-").sorted() }
    val nodes = connections.flatMap { it.toList() }.toSet()
    val neighbors = nodes.associateWith { node ->
      connections.filter { node in it }.flatten().toSet() - node
    }

    val cliques = findCliques(emptySet(), nodes, emptySet(), neighbors)

    return cliques.maxBy { it.size }.toSortedSet().joinToString(",")
  }

  private fun findCliques(
    current: Set<String>,
    potential: Set<String>,
    excluded: Set<String>,
    graph: Map<String, Set<String>>,
  ): Set<Set<String>> {
    return if (potential.isEmpty() && excluded.isEmpty()) {
      setOf(current)
    } else {
      val currPotential = potential.toMutableSet()
      val currExcluded = excluded.toMutableSet()
      val cliques = mutableSetOf<Set<String>>()
      while (currPotential.isNotEmpty()) {
        val next = currPotential.random()
        val nextNext = graph[next].orEmpty()
        val newCurrent = current + next
        val newPotential = currPotential.filter { it in nextNext }.toSet()
        val newExcluded = currExcluded.filter { it in nextNext }.toSet()
        cliques += findCliques(newCurrent, newPotential, newExcluded, graph)
        currPotential -= next
        currExcluded += next
      }
      cliques
    }
  }
}

fun main() = SomeDay.mainify(Day23)
