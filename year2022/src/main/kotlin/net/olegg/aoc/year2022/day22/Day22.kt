package net.olegg.aoc.year2022.day22

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.Directions.Companion.CCW
import net.olegg.aoc.utils.Directions.Companion.CW
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 22](https://adventofcode.com/2022/day/22)
 */
object Day22 : DayOf2022(22) {
  val pattern = "(\\d+)([RL])?".toRegex()
  override fun first(): Any? {
    val (rawMap, rawInstructions) = data.split("\n\n")
    val width = rawMap
      .lines()
      .maxOf { it.length }
    val map = rawMap
      .lines()
      .map { it.padEnd(width).toList() }

    val instructions = pattern
      .findAll(rawInstructions)
      .flatMap { match ->
        listOfNotNull(match.groupValues[1], match.groupValues.getOrNull(2)?.ifEmpty { null })
      }
      .toList()

    val position = Vector2D(map.first().indexOfFirst { it == '.' }, 0)
    val direction = R

    val (finalPosition, finalDirection) = instructions.fold(position to direction) { (pos, dir), instruction ->
      when (instruction) {
        "R" -> pos to CW.getValue(dir)
        "L" -> pos to CCW.getValue(dir)
        else -> {
          var curr = pos
          for (i in 0 until instruction.toInt()) {
            var next = curr + dir.step
            while (map[next] == null || map[next] == ' ') {
              next = if (map[next] == null) {
                Vector2D(
                  x = (next.x + map.first().size) % map.first().size,
                  y = (next.y + map.size) % map.size,
                )
              } else {
                next + dir.step
              }
            }

            if (map[next] == '.') {
              curr = next
            } else {
              break
            }
          }

          curr to dir
        }
      }
    }

    val dirs = listOf(R, D, L, U)

    return 1000 * (finalPosition.y + 1) + 4 * (finalPosition.x + 1) + dirs.indexOf(finalDirection)
  }
}

fun main() = SomeDay.mainify(Day22)
