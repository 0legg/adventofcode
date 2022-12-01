package net.olegg.aoc.year2019.day16

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2019.DayOf2019
import kotlin.math.abs

/**
 * See [Year 2019, Day 16](https://adventofcode.com/2019/day/16)
 */
object Day16 : DayOf2019(16) {
  override fun first(): Any? {
    val input = data.map { it - '0' }

    return (0..<100)
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

  override fun second(): Any? {
    val input = data.map { it - '0' }

    val position = data.substring(0, 7).toInt()

    val largeInput = sequence {
      repeat(10000) {
        yieldAll(input)
      }
    }

    val tail = largeInput.drop(position).toList().asReversed()

    return (0..<100)
      .fold(tail) { list, _ ->
        list.runningReduce { acc, item -> acc + item }
          .map { abs(it % 10) }
      }
      .takeLast(8)
      .reversed()
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
