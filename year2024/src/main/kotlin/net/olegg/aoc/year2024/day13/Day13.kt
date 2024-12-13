package net.olegg.aoc.year2024.day13

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.Vector2D
import net.olegg.aoc.year2024.DayOf2024

/**
 * See [Year 2024, Day 13](https://adventofcode.com/2024/day/13)
 */
object Day13 : DayOf2024(13) {
  private val digits = "(\\d+)".toRegex()
  override fun first(): Any? {
    val max = 100
    val rawConfigs = data.split("\n\n")
      .map { it.lines() }
      .map { config ->
        config.map { row ->
          val parsed = digits.findAll(row).map { it.value.toInt() }.toList()
          Vector2D(parsed.first(), parsed.last())
        }
      }

    return rawConfigs.sumOf { config ->
      val (buttonA, buttonB, prize) = config
      (0..max).flatMap { a ->
        (0..max).mapNotNull { b ->
          if (buttonA * a + buttonB * b == prize) (3 * a + b) else null
        }
      }.minOrNull() ?: 0
    }
  }
}

fun main() = SomeDay.mainify(Day13)
