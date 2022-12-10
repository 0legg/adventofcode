package net.olegg.aoc.year2022.day10

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 10](https://adventofcode.com/2022/day/10)
 */
object Day10 : DayOf2022(10) {
  private val times = listOf(
    20, 60, 100, 140, 180, 220,
  )
  override fun first(): Any? {
    val ops = lines.map { Op.parse(it) }

    val values = buildList {
      var x = 1L
      add(x)
      ops.forEach {
        repeat(it.cycles) {
          add(x)
        }
        x = it.apply(x)
      }
    }

    return times.sumOf { time -> time * values[time] }
  }

  sealed class Op(
    val cycles: Int,
  ) {
    open fun apply(x: Long): Long = x

    object Noop : Op(1)

    data class AddX(
      val value: Long,
    ) : Op(2) {
      override fun apply(x: Long): Long = x + value
    }

    companion object {
      fun parse(raw: String): Op {
        return when {
          raw == "noop" -> Noop
          raw.startsWith("addx ") -> AddX(raw.split(" ").last().toLong())
          else -> error("Unable to parse $raw")
        }
      }
    }
  }
}

fun main() = SomeDay.mainify(Day10)
