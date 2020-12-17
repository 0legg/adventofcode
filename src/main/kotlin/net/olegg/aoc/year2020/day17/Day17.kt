package net.olegg.aoc.year2020.day17

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector3D
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 17](https://adventofcode.com/2020/day/17)
 */
object Day17 : DayOf2020(17) {
  override fun first(data: String): Any? {
    val items = data
      .trim()
      .lines()
      .map { line -> line.map { it == '#' } }

    val initialState = items
      .flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, value ->
          if (value) Vector3D(x, y, 0) else null
        }
      }
      .toSet()

    val finalState = (0 until 6).fold(initialState) { state, _ ->
      val neighborCount = state.flatMap { it.neighbors() }
        .groupBy { it }
        .mapValues { it.value.size }

      return@fold (state.filter { neighborCount[it] in 2..3 } +
        neighborCount.filter { it.key !in state && it.value == 3 }.map { it.key })
        .toSet()
    }

    return finalState.size
  }

  fun Vector3D.neighbors(): List<Vector3D> {
    return (-1..1).flatMap { x ->
      (-1..1).flatMap { y ->
        (-1..1).mapNotNull { z ->
          Vector3D(this.x + x, this.y + y, this.z + z)
        }
      }
    } - this
  }
}

fun main() = SomeDay.mainify(Day17)
