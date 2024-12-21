package net.olegg.aoc.year2024.day21

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.utils.find
import net.olegg.aoc.utils.get
import net.olegg.aoc.utils.permutations
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 21](https://adventofcode.com/2024/day/21)
 */
object Day21 : DayOf2024(21) {
  private val pattern = "(\\d+)".toRegex()
  private val digits = """
    789
    456
    123
     0A
  """.trimIndent().lines().map { it.toList() }
  private val arms = """
     ^A
    <v>
  """.trimIndent().lines().map { it.toList() }

  override fun first(): Any? {
    val digitsMap = digits.flatMapIndexed { y, row ->
      row.mapIndexedNotNull { x, c ->
        if (c != ' ') c to Vector2D(x, y) else null
      }
    }.toMap()

    return lines.sumOf { line ->
      val code = pattern.find(line)?.value?.toIntOrNull() ?: 0

      val length = "A$line"
        .zipWithNext { a, b -> bestPath(digits, arms, digitsMap[a]!!, digitsMap[b]!!, 0) }
        .sum()

      code * length
    }
  }

  private val bestCache = mutableMapOf<Triple<Vector2D, Vector2D, Int>, Int>()

  private fun bestPath(
    matrix: List<List<Char>>,
    handler: List<List<Char>>,
    from: Vector2D,
    to: Vector2D,
    level: Int,
  ): Int {
    val config = Triple(from, to, level)
    return when {
      level == 3 -> 1
      config in bestCache -> bestCache[config]!!
      from == to -> 1
      else -> {
        val delta = to - from
        val basePath = buildList {
          when {
            delta.x > 0 -> repeat(delta.x) { add(Directions.R) }
            delta.x < 0 -> repeat(-delta.x) { add(Directions.L) }
          }
          when {
            delta.y > 0 -> repeat(delta.y) { add(Directions.D) }
            delta.y < 0 -> repeat(-delta.y) { add(Directions.U) }
          }
        }

        val best = basePath.permutations()
          .filter { path ->
            path.scan(from) { curr, dir -> curr + dir.step }.none { matrix[it] == ' ' }
          }
          .minOf { path ->
            val chars = buildList {
              add('A')
              path.forEach {
                when (it) {
                  Directions.L -> add('<')
                  Directions.R -> add('>')
                  Directions.U -> add('^')
                  Directions.D -> add('v')
                  else -> Unit
                }
              }
              add('A')
            }

            chars.zipWithNext().sumOf { (fromChar, toChar) ->
              val fromPoint = handler.find(fromChar)!!
              val toPoint = handler.find(toChar)!!
              bestPath(handler, handler, fromPoint, toPoint, level + 1)
            }
          }

        bestCache[config] = best
        best
      }
    }
  }
}

fun main() = SomeDay.mainify(Day21)
