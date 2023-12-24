package net.olegg.aoc.year2023.day24

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.pairs
import net.olegg.aoc.utils.parseBigInts
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2023.DayOf2023
import java.math.BigInteger
import kotlin.math.sign


/**
 * See [Year 2023, Day 24](https://adventofcode.com/2023/day/24)
 */
object Day24 : DayOf2023(24) {
  private const val MIN = 200000000000000L.toDouble()
  private const val MAX = 400000000000000L.toDouble()

  override fun first(): Any? {
    val stones = lines.map { it.split(" @ ").toPair() }
      .map { it.first.parseBigInts(", ") to it.second.parseBigInts(", ") }

    return stones.pairs()
      .mapNotNull { (first, second) ->
        val (x1, y1) = first.first
        val (dx1, dy1) = first.second
        val (x2, y2) = x1 + dx1 to y1 + dy1

        val (x3, y3) = second.first
        val (dx3, dy3) = second.second
        val (x4, y4) = x3 + dx3 to y3 + dy3

        val d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4)
        if (d == BigInteger.ZERO) {
          return@mapNotNull null
        }

        val xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)).toDouble() / d.toDouble()
        val yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)).toDouble() / d.toDouble()

        (xi to yi).takeIf {
          dx1.toDouble().sign == (xi - x1.toDouble()).sign &&
            dy1.toDouble().sign == (yi - y1.toDouble()).sign &&
            dx3.toDouble().sign == (xi - x3.toDouble()).sign &&
            dy3.toDouble().sign == (yi - y3.toDouble()).sign
        }
      }
      .count { (x, y) ->
        x in MIN..MAX && y in MIN..MAX
      }
  }
}

fun main() = SomeDay.mainify(Day24)
