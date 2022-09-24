package net.olegg.aoc.year2021.day15

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Neighbors4
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.fit
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.set
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 15](https://adventofcode.com/2021/day/15)
 */
object Day15 : DayOf2021(15) {
  override fun first(data: String): Any? {
    val map = data.trim()
      .lines()
      .map { line -> 
        line.map { it.digitToInt() }
      }

    return solve(map)
  }

  override fun second(data: String): Any? {
    val map = data.trim()
      .lines()
      .map { line ->
        line.map { it.digitToInt() }
      }

    val mappings = (1..9).associateWith { start ->
      (1..9).scan(start) { acc, _ ->
        if (acc < 9) acc + 1 else 1
      }
    }

    val largeMap = (0..4).flatMap { dy ->
      map.map { row ->
        (0..4).flatMap { dx ->
          row.map { value ->
            mappings.getValue(value)[dx + dy]
          }
        }
      }
    }

    return solve(largeMap)
  }

  private fun solve(map: List<List<Int>>): Int {
    val best = map.map { line ->
      line.map { 1_000_000 }.toMutableList()
    }
    best[Vector2D(0, 0)] = 0

    val queue = ArrayDeque(listOf(Vector2D(0, 0)))
    while (queue.isNotEmpty()) {
      val curr = queue.removeFirst()
      val value = best[curr] ?: 1_000_000

      Neighbors4.map { curr + it.step }
        .filter { map.fit(it) }
        .map { it to value + map[it]!! }
        .filter { it.second < best[it.first]!! }
        .forEach { (next, nextVal) ->
          best[next] = nextVal
          queue += next
        }
    }

    return best.last().last()
  }
}

fun main() = SomeDay.mainify(Day15)
