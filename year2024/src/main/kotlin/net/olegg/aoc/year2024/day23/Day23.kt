package net.olegg.aoc.year2024.day23

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.utils.transpose
import net.olegg.aoc.year2024.DayOf2024
import kotlin.math.absoluteValue

/**
 * See [Year 2024, Day 23](https://adventofcode.com/2024/day/23)
 */
object Day23 : DayOf2024(23) {
  override fun first(): Any? {
    val connections = lines.map { it.split("-").sorted() }

    return connections.flatMapIndexed { i, connI ->
      connections.drop(i + 1).flatMapIndexed { j, connJ ->
        val subset = (connI + connJ).toSet()
        if (subset.size == 3) {
          connections.drop(i + 1 + j + 1).mapNotNull { connK ->
            (subset + connK).toSet().takeIf { it.size == 3 }
          }
        } else {
          emptyList()
        }
      }
    }.count { set ->
      set.any { it.startsWith("t") }
    }
  }
}

fun main() = SomeDay.mainify(Day23)
