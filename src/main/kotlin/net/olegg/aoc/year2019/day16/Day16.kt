package net.olegg.aoc.year2019.day16

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2019.DayOf2019
import kotlin.math.abs

/**
 * See [Year 2019, Day 16](https://adventofcode.com/2019/day/16)
 */
object Day16 : DayOf2019(16) {
  override fun first(data: String): Any? {
    val input = data
        .trim()
        .map { it - '0' }

    return (0 until 100)
        .fold(input) { list, _ ->
          list.indices.map { index ->
            list.asSequence()
                .zip(makePhase(index + 1)) { a, b -> a * b }
                .sum()
                .let { abs(it % 10) }
          }
        }
        .take(8)
        .joinToString(separator = "")
  }

  private val BASE_PHASE = listOf(0, 1, 0, -1)

  private fun makePhase(index: Int): Sequence<Int> {
    return sequence {
      while (true) {
        BASE_PHASE.forEach { base ->
          repeat(index) {
            yield(base)
          }
        }
      }
    }.drop(1)
  }
}

fun main() = SomeDay.mainify(Day16)
