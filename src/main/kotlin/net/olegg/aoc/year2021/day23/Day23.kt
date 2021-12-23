package net.olegg.aoc.year2021.day23

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2021.DayOf2021
import java.util.PriorityQueue
import kotlin.math.abs

/**
 * See [Year 2021, Day 23](https://adventofcode.com/2021/day/23)
 */
object Day23 : DayOf2021(23) {
  private val cost = mapOf(
    'A' to 1,
    'B' to 10,
    'C' to 100,
    'D' to 1000,
  )
  
  override fun first(data: String): Any? {
    val points = data.trim()
      .lines()
      .flatMapIndexed { y, row ->
        row.mapIndexedNotNull { x, c ->
          if (c in 'A'..'D' || c == '.') Vector2D(x, y) to c else null
        }
      }

    val amphipods = points.filter { it.second in 'A'..'D' }

    val stacks = amphipods
      .groupBy { it.first.x }
      .toList()
      .zip('A'..'D') { (x, points), char ->
        Triple(char, x, points.map { it.first }.toSet())
      }

    val entries = stacks.associate { it.first to Vector2D(it.second, 1) }
    val targets = stacks.associate { it.first to it.third }
    val spots = points.filter { it.second == '.' }
      .map { it.first }
      .filterNot { it in entries.values }

    val minX = spots.minOf { it.x }
    val maxX = spots.maxOf { it.x }

    val queue = PriorityQueue<World>(1_000_000, compareBy { it.score })
    val seen = mutableSetOf<List<Triple<Vector2D, Char, Boolean>>>()
    queue += World(amphipods.map { Triple(it.first, it.second, false) }, 0)

    while (queue.isNotEmpty()) {
      val (curr, score) = queue.remove()
      if (curr in seen) {
        continue
      } else {
        seen += curr
      }
      if (curr.all { it.first in targets[it.second].orEmpty() }) {
        return score
      }

      val occupied = curr.map { it.first }.toSet()
      val freeSpots = spots - occupied

      val canMove = curr.filter { it.first - Vector2D(0, 1) !in occupied }

      val exiting = canMove.filterNot { it.third }
        .flatMap { amph ->
          val toLeft = (amph.first.x - 1 downTo minX).map { Vector2D(it, 1) }.takeWhile { it !in occupied }
          val toRight = (amph.first.x + 1..maxX).map { Vector2D(it, 1) }.takeWhile { it !in occupied }
          (toLeft + toRight).filter { it in freeSpots }.map { amph to it }
        }

      val availableEntries = targets
        .filter { (char, points) ->
          curr.none { it.first in points && it.second != char } && points.any { it !in occupied }
        }
        .mapValues { stack ->
          stack.value.filter { it !in occupied }.maxByOrNull { it.y }!!
        }
        .toList()

      val entering = availableEntries.flatMap { (char, point) ->
        val matching = canMove.filter { it.second == char }
          .filterNot { it.first.x == point.x }
        val fromLeft = matching.filter { it.first.x < point.x }
          .filter {
            (it.first.x + 1..point.x).none { x -> Vector2D(x, 1) in occupied }
          }
        val fromRight = matching.filter { it.first.x > point.x }
          .filter {
            (it.first.x - 1 downTo point.x).none { x -> Vector2D(x, 1) in occupied }
          }
        (fromLeft + fromRight).map { it to point }
      }

      (entering + exiting).forEach { (amph, to) ->
        val price = ((amph.first.y - 1) + (to.y - 1) + abs(amph.first.x - to.x)) * cost[amph.second]!!
        queue += World(
          amphipods = curr.map {
            if (it == amph) Triple(to, amph.second, true) else it
          },
          score = score + price,
        )
      }
    }
    return -1
  }

  data class World(
    val amphipods: List<Triple<Vector2D, Char, Boolean>>,
    val score: Int,
  )
}

fun main() = SomeDay.mainify(Day23)
