package net.olegg.aoc.year2024.day15

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.find
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 15](https://adventofcode.com/2024/day/15)
 */
object Day15 : DayOf2024(15) {
  private val dirs = mapOf(
    '^' to U,
    'v' to D,
    '<' to L,
    '>' to R,
  )

  override fun first(): Any? {
    val (rawMap, rawOps) = data.split("\n\n")
    val ops = rawOps.filter { it in dirs }

    val startMap = rawMap.lines().map { it.toList() }
    val startRobot = startMap.find('@')!!
    val startBoxes = buildSet {
      startMap.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
          if (c == 'O' || c == '@') {
            add(Vector2D(x, y))
          }
        }
      }
    }

    val walls = buildSet {
      startMap.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
          if (c == '#') {
            add(Vector2D(x, y))
          }
        }
      }
    }

    val (finalRobot, finalBoxes) = ops.fold(startRobot to startBoxes) { (robot, boxes), op ->
      val dir = dirs.getValue(op)

      val toMove = generateSequence(robot) { it + dir.step }
        .takeWhile { it in boxes }
        .toList()

      if (toMove.last() + dir.step in walls) {
        robot to boxes
      } else {
        val boxesToStay = boxes - toMove.toSet()

        (robot + dir.step) to (boxesToStay + toMove.map { it + dir.step }.toSet())
      }
    }

    return (finalBoxes - finalRobot).sumOf { it.y * 100 + it.x }
  }
}

fun main() = SomeDay.mainify(Day15)
