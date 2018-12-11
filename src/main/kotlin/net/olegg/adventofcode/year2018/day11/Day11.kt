package net.olegg.adventofcode.year2018.day11

import net.olegg.adventofcode.someday.SomeDay
import net.olegg.adventofcode.year2018.DayOf2018

/**
 * @see <a href="http://adventofcode.com/2018/day/11">Year 2018, Day 11</a>
 */
class Day11 : DayOf2018(11) {
  override fun first(data: String): Any? {
    val serial = data.trim().toInt()

    val grid = (1..300).map { y ->
      (1..300).map { x ->
        val rack = x + 10
        val start = rack * y
        val withSerial = start + serial
        val byId = withSerial * rack
        val digit = (byId / 100) % 10
        digit - 5
      }
    }

    return (1..298).flatMap { y ->
      (1..298).map { x ->
        val sum = (-1..1).sumBy { dy -> (-1..1).sumBy { dx -> grid[y + dy][x + dx] } }
        (x to y) to  sum
      }
    }
        .maxBy { it.second }
        ?.let { "${it.first.first},${it.first.second}" }
  }
}

fun main(args: Array<String>) = SomeDay.mainify(Day11::class)
