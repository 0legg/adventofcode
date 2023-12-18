package net.olegg.aoc.year2023.day18

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.set
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 18](https://adventofcode.com/2023/day/18)
 */
object Day18 : DayOf2023(18) {
  override fun first(): Any? {
    val steps = lines.map { it.split(" ").toPair() }
      .map { Directions.valueOf(it.first) to it.second.toInt() }

    val map = mutableSetOf(Vector2D())
    steps.fold(Vector2D()) { acc, (step, count) ->
      (1..count).scan(acc) { smallAcc, _ ->
        smallAcc + step.step
      }.onEach { map += it }.last()
    }

    val minX = map.minOf { it.x } - 1
    val maxX = map.maxOf { it.x } + 1
    val minY = map.minOf { it.y } - 1
    val maxY = map.maxOf { it.y } + 1

    val shift = Vector2D(minX, minY)

    val fill = List(maxY - minY + 1) { y ->
      MutableList(maxX - minX + 1) { x ->
        if (Vector2D(x, y) + shift in map) '#' else ' '
      }
    }

    val queue = ArrayDeque(listOf(Vector2D()))
    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      if (fill[curr] != ' ') {
        continue
      }
      fill[curr] = '.'

      queue += Directions.NEXT_4
        .map { curr + it.step }
        .filter { fill[it] == ' ' }
    }

    return fill.sumOf { row ->
      row.count { it != '.' }
    }
  }
}

fun main() = SomeDay.mainify(Day18)
