package net.olegg.aoc.year2022.day10

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2022.DayOf2022
import kotlin.math.abs

/**
 * See [Year 2022, Day 10](https://adventofcode.com/2022/day/10)
 */
object Day10 : DayOf2022(10) {
  private val TIMES = listOf(
    20,
    60,
    100,
    140,
    180,
    220,
  )

  override fun first(): Any? {
    val ops = lines.map { Op.parse(it) }

    val values = ops
      .runningFold(1 to emptyList<Int>()) { (last, _), op ->
        op.apply(last) to List(op.cycles) { last }
      }
      .flatMap { it.second }

    return TIMES.sumOf { time -> time * values[time - 1] }
  }

  override fun second(): Any? {
    val ops = lines.map { Op.parse(it) }
    val values = ops
      .runningFold(1 to emptyList<Int>()) { (last, _), op ->
        op.apply(last) to List(op.cycles) { last }
      }
      .flatMap { it.second }

    val lit = values.chunked(40) { row ->
      row.mapIndexed { index, position ->
        abs(index - position) < 2
      }
    }

    return lit.joinToString(separator = "\n", prefix = "\n") {
        row ->
      row.joinToString(separator = "") {
        if (it) "██" else ".."
      }
    }
  }

  sealed class Op(
    val cycles: Int,
  ) {
    open fun apply(x: Int): Int = x

    object Noop : Op(1)

    data class AddX(
      val value: Int,
    ) : Op(2) {
      override fun apply(x: Int): Int = x + value
    }

    companion object {
      fun parse(raw: String): Op {
        return when {
          raw == "noop" -> Noop
          raw.startsWith("addx ") -> AddX(raw.split(" ").last().toInt())
          else -> error("Unable to parse $raw")
        }
      }
    }
  }
}

fun main() = SomeDay.mainify(Day10)
