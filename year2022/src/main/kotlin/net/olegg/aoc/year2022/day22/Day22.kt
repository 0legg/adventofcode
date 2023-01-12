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
  private val pattern = "(\\d+)([RL])?".toRegex()
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

  override fun second(): Any? {
    val (rawMap, rawInstructions) = data.split("\n\n")
    val width = rawMap
      .lines()
      .maxOf { it.length }
    val map = rawMap
      .lines()
      .map { it.padEnd(width).toList() }

    val top = Vector2D(50, 0) to Vector2D(99, 49)
    val front = Vector2D(50, 50) to Vector2D(99, 99)
    val bottom = Vector2D(50, 100) to Vector2D(99, 149)
    val right = Vector2D(100, 0) to Vector2D(149, 49)
    val left = Vector2D(0, 100) to Vector2D(49, 149)
    val back = Vector2D(0, 150) to Vector2D(49, 199)
    val planes = listOf(top, front, bottom, right, left, back)

    val instructions = pattern
      .findAll(rawInstructions)
      .flatMap { match ->
        listOfNotNull(match.groupValues[1], match.groupValues.getOrNull(2)?.ifEmpty { null })
      }
      .toList()

    val startPos = Vector2D(map.first().indexOfFirst { it == '.' }, 0)
    val startDir = R

    val (finalPos, finalDir) = instructions.fold(startPos to startDir) { (pos, dir), move ->
      when (move) {
        "R" -> pos to CW.getValue(dir)
        "L" -> pos to CCW.getValue(dir)
        else -> {
          var currPos = pos
          var currDir = dir
          val currPlane = planes.first {
            currPos.x in it.first.x..it.second.x && currPos.y in it.first.y..it.second.y
          }

          for (i in 0 until move.toInt()) {
            var nextPos = currPos + currDir.step
            var nextDir = currDir
            if (map[nextPos] == null || map[nextPos] == ' ') {
              val (fixedDir, fixedPos) = when {
                currPlane == top && currDir == U -> R to Vector2D(0, 100 + currPos.x)
                currPlane == top && currDir == L -> R to Vector2D(0, 150 - currPos.y - 1)
                currPlane == right && currDir == U -> U to Vector2D(currPos.x - 100, 199)
                currPlane == right && currDir == R -> L to Vector2D(99, 150 - currPos.y - 1)
                currPlane == right && currDir == D -> L to Vector2D(99, currPos.x - 50)
                currPlane == front && currDir == L -> D to Vector2D(currPos.y - 50, 100)
                currPlane == front && currDir == R -> U to Vector2D(currPos.y + 50, 49)
                currPlane == bottom && currDir == R -> L to Vector2D(149, 150 - currPos.y - 1)
                currPlane == bottom && currDir == D -> L to Vector2D(49, currPos.x + 100)
                currPlane == left && currDir == U -> R to Vector2D(50, currPos.x + 50)
                currPlane == left && currDir == L -> R to Vector2D(50, 150 - currPos.y - 1)
                currPlane == back && currDir == L -> D to Vector2D(currPos.y - 100, 0)
                currPlane == back && currDir == D -> D to Vector2D(currPos.x + 100, 0)
                currPlane == back && currDir == R -> U to Vector2D(currPos.y - 100, 149)
                else -> error("$nextDir to $nextPos")
              }
              nextDir = fixedDir
              nextPos = fixedPos
            }

            if (map[nextPos] == '.') {
              currPos = nextPos
              currDir = nextDir
            } else {
              break
            }
          }
          currPos to currDir
        }
      }
    }

    val dirs = listOf(R, D, L, U)

    return 1000 * (finalPos.y + 1) + 4 * (finalPos.x + 1) + dirs.indexOf(finalDir)
  }
}

fun main() = SomeDay.mainify(Day22)
