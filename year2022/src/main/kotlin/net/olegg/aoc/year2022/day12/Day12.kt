package net.olegg.aoc.year2022.day12

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.fit
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.set
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 12](https://adventofcode.com/2022/day/12)
 */
object Day12 : DayOf2022(12) {
  override fun first(): Any? {
    val map = matrix.map { it.toMutableList() }
    val from = map.mapIndexedNotNull { y, row ->
      row.mapIndexedNotNull { x, char ->
        if (char == 'S') Vector2D(x, y) else null
      }.firstOrNull()
    }.first()
    map[from] = 'a'

    val to = map.mapIndexedNotNull { y, row ->
      row.mapIndexedNotNull { x, char ->
        if (char == 'E') Vector2D(x, y) else null
      }.firstOrNull()
    }.first()
    map[to] = 'z'

    val visited = mutableSetOf<Vector2D>()
    val queue = ArrayDeque(listOf(from to 0))

    while (queue.isNotEmpty()) {
      val (curr, step) = queue.removeFirst()
      if (curr == to) {
        return step
      }
      if (curr in visited) {
        continue
      }
      visited += curr

      queue += Neighbors4.map { curr + it.step }
        .filter { map.fit(it) }
        .filter { it !in visited }
        .filter { map[it]!!- map[curr]!! <= 1 }
        .map { it to step + 1 }
    }

    return null
  }

  override fun second(): Any? {
    val map = matrix.map { it.toMutableList() }
    val from = map.mapIndexedNotNull { y, row ->
      row.mapIndexedNotNull { x, char ->
        if (char == 'S') Vector2D(x, y) else null
      }.firstOrNull()
    }.first()
    map[from] = 'a'

    val to = map.mapIndexedNotNull { y, row ->
      row.mapIndexedNotNull { x, char ->
        if (char == 'E') Vector2D(x, y) else null
      }.firstOrNull()
    }.first()
    map[to] = 'z'

    val visited = mutableSetOf<Vector2D>()
    val queue = ArrayDeque(listOf(to to 0))

    while (queue.isNotEmpty()) {
      val (curr, step) = queue.removeFirst()
      if (map[curr] == 'a') {
        return step
      }
      if (curr in visited) {
        continue
      }
      visited += curr

      queue += Neighbors4.map { curr + it.step }
        .filter { map.fit(it) }
        .filter { it !in visited }
        .filter { map[it]!!- map[curr]!! >= -1 }
        .map { it to step + 1 }
    }

    return null
  }
}

fun main() = SomeDay.mainify(Day12)
