package net.olegg.aoc.year2015.day16

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2015.DayOf2015

/**
 * See [Year 2015, Day 16](https://adventofcode.com/2015/day/16)
 */
object Day16 : DayOf2015(16) {
  val PATTERN = "^Sue (\\d+): (?:([a-z]+): (\\d+)(?:, )?)*$".toRegex()

  val sues = data
    .trim()
    .lines()
    .mapNotNull { line ->
      PATTERN.matchEntire(line)?.let { match ->
        println(match.groupValues.joinToString(separator = "\n"))
        println()
        val index = match.groupValues[1].toInt()
        val own = match.groupValues
          .drop(2)
          .windowed(2)
          .map { it.first() to it.last().toInt() }
          .toMap()
        return@let index to own
      }
    }

  val footprint = mapOf(
    "children" to 3,
    "cats" to 7,
    "samoyeds" to 2,
    "pomeranians" to 3,
    "akitas" to 0,
    "vizslas" to 0,
    "goldfish" to 5,
    "trees" to 3,
    "cars" to 2,
    "perfumes" to 1
  )

  override fun first(data: String): Any? {
    println(sues)
    return sues.filter { it.second.all { it.value == footprint[it.key] } }.map { it.first }.first()
  }

  override fun second(data: String): Any? {
    return sues
      .first { (_, own) ->
        own.all { (key, value) ->
          when (key) {
            "cats", "trees" -> (value > footprint[key] ?: 0)
            "pomeranians", "goldfish" -> (value < footprint[key] ?: 0)
            else -> value == footprint[key]
          }
        }
      }
      .first
  }
}

fun main() = SomeDay.mainify(Day16)
