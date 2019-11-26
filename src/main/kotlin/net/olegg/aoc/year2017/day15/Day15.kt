package net.olegg.aoc.year2017.day15

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 15](https://adventofcode.com/2017/day/15)
 */
object Day15 : DayOf2017(15) {
  override fun first(data: String): Any? {
    val generators = data.trimIndent()
        .lines()
        .map { it.split("\\s+".toRegex()).last().toLong() }

    val genA = generateSequence(generators[0]) { (it * 16807L) % Int.MAX_VALUE.toLong() }
    val genB = generateSequence(generators[1]) { (it * 48271L) % Int.MAX_VALUE.toLong() }

    return genA.zip(genB)
        .take(40_000_000)
        .count { (it.first and 65535) == (it.second and 65535) }
  }

  override fun second(data: String): Any? {
    val generators = data.trimIndent()
        .lines()
        .map { it.split("\\s+".toRegex()).last().toLong() }

    val genA = generateSequence(generators[0]) { (it * 16807L) % Int.MAX_VALUE.toLong() }
        .filter { it % 4 == 0L }
    val genB = generateSequence(generators[1]) { (it * 48271L) % Int.MAX_VALUE.toLong() }
        .filter { it % 8 == 0L }

    return genA.zip(genB)
        .take(5_000_000)
        .count { (it.first and 65535) == (it.second and 65535) }
  }
}

fun main() = SomeDay.mainify(Day15)
