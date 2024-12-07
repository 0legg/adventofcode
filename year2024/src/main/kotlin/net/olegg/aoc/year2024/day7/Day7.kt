package net.olegg.aoc.year2024.day7

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseBigInts
import net.olegg.aoc.year2024.DayOf2024
import java.math.BigInteger
import kotlin.math.pow

/**
 * See [Year 2024, Day 7](https://adventofcode.com/2024/day/7)
 */
object Day7 : DayOf2024(7) {
  override fun first(): Any? {
    val equations = lines.map { it.parseBigInts(":", " ") }

    return equations
      .filter { equation ->
        val result = equation.first()
        val terms = equation.drop(1)
        val opsCount = terms.size - 1

        (0..1.shl(opsCount)).any { mask ->
          val ops = mask.toString(2).padStart(opsCount, '0').toList()

          result == terms.drop(1).zip(ops)
            .fold(terms.first()) { acc, (value, op) ->
              if (op == '0') acc + value else acc * value
            }
        }
      }
      .sumOf { it.first() }
  }

  override fun second(): Any? {
    val equations = lines.map { it.parseBigInts(":", " ") }

    return equations
      .filter { equation ->
        val result = equation.first()
        val terms = equation.drop(1)
        val opsCount = terms.size - 1

        (0..3.0.pow(opsCount.toDouble()).toInt()).any { mask ->
          val ops = mask.toString(3).padStart(opsCount, '0').toList()

          result == terms.drop(1).zip(ops)
            .fold(terms.first()) { acc, (value, op) ->
              when (op) {
                '0' -> acc + value
                '1' -> acc * value
                '2' -> BigInteger("$acc$value")
                else -> acc
              }
            }
        }
      }
      .sumOf { it.first() }
  }
}

fun main() = SomeDay.mainify(Day7)
