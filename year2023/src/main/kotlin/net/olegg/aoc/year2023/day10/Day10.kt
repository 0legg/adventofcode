package net.olegg.aoc.year2023.day10

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Directions.Companion.NEXT_4
import net.olegg.aoc.utils.Directions.D
import net.olegg.aoc.utils.Directions.L
import net.olegg.aoc.utils.Directions.R
import net.olegg.aoc.utils.Directions.U
import net.olegg.aoc.utils.find
import net.olegg.aoc.utils.get
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 10](https://adventofcode.com/2023/day/10)
 */
object Day10 : DayOf2023(10) {
  private val PIPES = setOf('|', '-')
  private val JOINTS = setOf('J', 'L', '7', 'F')

  private val STEPS = mapOf(
    ('-' to L.step) to L.step,
    ('-' to R.step) to R.step,

    ('|' to U.step) to U.step,
    ('|' to D.step) to D.step,

    ('J' to D.step) to L.step,
    ('J' to R.step) to U.step,

    ('L' to D.step) to R.step,
    ('L' to L.step) to U.step,

    ('7' to R.step) to D.step,
    ('7' to U.step) to L.step,

    ('F' to L.step) to D.step,
    ('F' to U.step) to R.step,
  )

  override fun first(): Any? {
    val start = matrix.find('S')!!

    val next = NEXT_4.first {
      val char = matrix[start + it.step]
      char in PIPES && (char to it.step) in STEPS
    }

    return generateSequence { 0 }
      .scan(start + next.step to next.step) { (currPos, currDir), _ ->
        val nextPos = currPos + currDir
        println(matrix[nextPos])
        val nextDir = when (val char = matrix[nextPos]!!) {
          in PIPES -> STEPS.getValue(char to currDir)
          in JOINTS -> STEPS.getValue(char to currDir)
          else -> currDir
        }

        nextPos to nextDir
      }
      .takeWhile { it.first != start }
      .count()
      .let { (it + 1) / 2 }
  }
}

fun main() = SomeDay.mainify(Day10)
