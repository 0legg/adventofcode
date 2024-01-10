package net.olegg.aoc.year2023.day21

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.find
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 21](https://adventofcode.com/2023/day/21)
 */
object Day21 : DayOf2023(21) {
  override fun first(): Any? {
    val start = matrix.find('S')!!

    val toVisit = mutableMapOf(0 to mutableSetOf(start))

    (0..<64).forEach { step ->
      val nodes = ArrayDeque(toVisit.getValue(step))
      val next = mutableSetOf<Vector2D>()
      while (nodes.isNotEmpty()) {
        val curr = nodes.removeFirst()
        next += Directions.NEXT_4
          .map { curr + it.step }
          .filter { matrix[it] == '.' || matrix[it] == 'S' }
      }
      toVisit[step + 1] = next
    }

    return toVisit[64]!!.size
  }

  override fun second(): Any? {
    val cover = 3
    val max = 26501365
    val start = matrix.find('S')!!
    val size = matrix.size
    val tail = max % size
    val maxTile = (max / size).toLong()

    val toVisit = mutableMapOf(0 to mutableSetOf(start))

    (0..<(size * cover + tail)).forEach { step ->
      val nodes = ArrayDeque(toVisit.getValue(step))
      val next = mutableSetOf<Vector2D>()
      while (nodes.isNotEmpty()) {
        val curr = nodes.removeFirst()
        next += Directions.NEXT_4
          .map { curr + it.step }
          .filter {
            val fit = Vector2D(
              ((it.x % size) + size) % size,
              ((it.y % size) + size) % size,
            )
            matrix[fit] == '.' || matrix[fit] == 'S'
          }
      }
      toVisit[step + 1] = next
    }

    val map = (-cover..cover).map { y ->
      val startY = y * size
      val endY = (y + 1) * size
      (-cover..cover).map { x ->
        val startX = x * size
        val endX = (x + 1) * size

        toVisit[tail + size * cover]!!.count { it.x in startX..<endX && it.y in startY..<endY }
      }
    }

    return (map[3][0] + map[0][3] + map[3][6] + map[6][3]) +
      maxTile * (map[2][0] + map[4][0] + map[2][6] + map[4][6]) +
      (maxTile - 1) * (map[2][1] + map[4][1] + map[2][5] + map[4][5]) +
      (maxTile - 1) * (maxTile - 1) * map[3][4] +
      (maxTile * maxTile * map[3][3])
  }
}

fun main() = SomeDay.mainify(Day21)
