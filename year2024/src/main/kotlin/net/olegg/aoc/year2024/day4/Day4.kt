package net.olegg.aoc.year2024.day4

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Directions.DL
import net.olegg.aoc.utils.Directions.DR
import net.olegg.aoc.utils.Directions.UL
import net.olegg.aoc.utils.Directions.UR
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.permutations
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 4](https://adventofcode.com/2024/day/4)
 */
object Day4 : DayOf2024(4) {
  override fun first(): Any? {
    val magic = "XMAS"
    val magicList = magic.toList()

    return matrix.flatMapIndexed { y, row ->
      row.flatMapIndexed { x, _ ->
        val curr = Vector2D(x, y)
        Directions.NEXT_8.mapNotNull { dir ->
          magic.indices.map { curr + dir.step * it }
            .mapNotNull { matrix[it] }
            .takeIf { it == magicList }
        }
      }
    }.count()
  }

  override fun second(): Any? {
    val corners = listOf(
      listOf(listOf(UL, UR), listOf(DL, DR)),
      listOf(listOf(UL, DL), listOf(UR, DR)),
    )
    val letters = listOf('M', 'S')

    return matrix.flatMapIndexed { y, row ->
      row.mapIndexed { x, c ->
        val curr = Vector2D(x, y)
        when {
          c != 'A' -> 0
          corners.none { currCorners ->
            letters.permutations().any { currLetters ->
              currCorners.zip(currLetters).all { (corner, letter) ->
                corner.all { dir -> matrix[curr + dir.step] == letter }
              }
            }
          } -> 0
          else -> 1
        }
      }
    }.sum()
  }
}

fun main() = SomeDay.mainify(Day4)
