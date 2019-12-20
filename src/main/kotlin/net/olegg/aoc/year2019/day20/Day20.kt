package net.olegg.aoc.year2019.day20

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2019.DayOf2019
import java.util.ArrayDeque

/**
 * See [Year 2019, Day 20](https://adventofcode.com/2019/day/20)
 */
object Day20 : DayOf2019(20) {
  override fun first(data: String): Any? {
    val map = data
        .lines()
        .filter { it.isNotEmpty() }
        .map { it.toList() }

    val portals = mutableMapOf<String, MutableList<Vector2D>>()

    map.forEachIndexed { y, row ->
      row.forEachIndexed { x, c ->
        if (c in 'A'..'Z') {
          val position = Vector2D(x, y)
          val r = map[position + Vector2D(1, 0)]
          val b = map[position + Vector2D(0, 1)]
          if (r in 'A'..'Z') {
            val portalR = position + Vector2D(2, 0)
            if (map[portalR] == '.') {
              portals.getOrPut("$c$r") { mutableListOf() } += portalR
            }
            val portalL = position - Vector2D(1, 0)
            if (map[portalL] == '.') {
              portals.getOrPut("$c$r") { mutableListOf() } += portalL
            }
          }
          if (b in 'A'..'Z') {
            val portalB = position + Vector2D(0, 2)
            if (map[portalB] == '.') {
              portals.getOrPut("$c$b") { mutableListOf() } += portalB
            }
            val portalT = position - Vector2D(0, 1)
            if (map[portalT] == '.') {
              portals.getOrPut("$c$b") { mutableListOf() } += portalT
            }
          }
        }
      }
    }

    val routes = portals.filter { it.value.size == 2 }
        .flatMap { listOf(it.value.first() to it.value.last(), it.value.last() to it.value.first()) }
        .toMap()

    val start = portals["AA"].orEmpty().first()
    val target = portals["ZZ"].orEmpty().first()
    val queue = ArrayDeque(listOf(start to 0))
    val visited = mutableSetOf(start)

    while (queue.isNotEmpty()) {
      val curr = queue.pop()
      if (curr.first == target) {
        return curr.second
      }

      (Neighbors4.map { it.step + curr.first } + listOfNotNull(routes[curr.first]))
          .filter { map[it] == '.' }
          .filter { it !in visited }
          .forEach {
            visited += it
            queue.offer(it to curr.second + 1)
          }
    }

    return Int.MAX_VALUE
  }

  private operator fun <T> List<List<T>>.get(v: Vector2D): T? = when {
    v.y !in indices -> null
    v.x !in this[v.y].indices -> null
    else -> this[v.y][v.x]
  }
}

fun main() = SomeDay.mainify(Day20)
