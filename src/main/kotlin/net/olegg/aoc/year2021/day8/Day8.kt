package net.olegg.aoc.year2021.day8

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2021.DayOf2021

/**
 * See [Year 2021, Day 8](https://adventofcode.com/2021/day/8)
 */
object Day8 : DayOf2021(8) {
  override fun first(): Any? {
    return lines
      .map { it.split(" | ").last() }
      .flatMap { it.split(" ") }
      .count { it.length in setOf(2, 3, 4, 7) }
  }

  override fun second(): Any? {
    val sets = mapOf(
      "abcefg" to "0",
      "cf" to "1",
      "acdeg" to "2",
      "acdfg" to "3",
      "bcdf" to "4",
      "abdfg" to "5",
      "abdefg" to "6",
      "acf" to "7",
      "abcdefg" to "8",
      "abcdfg" to "9",
    ).mapKeys { it.key.toSet() }

    return lines
      .sumOf { line ->
        val (sourceLine, fourLine) = line.split(" | ")

        val sources = sourceLine.split(" ").map { it.toSet() }
        val mappings = mutableMapOf<Char, Char>()
        val digits = Array(10) { emptySet<Char>() }
        digits[1] = sources.first { it.size == 2 }
        digits[4] = sources.first { it.size == 4 }
        digits[7] = sources.first { it.size == 3 }
        digits[8] = sources.first { it.size == 7 }
        mappings['a'] = (digits[7] - digits[1]).first()
        digits[6] = sources.filter { it.size == 6 }
          .first { (it + digits[1]).size == 7 }
        mappings['c'] = (digits[8] - digits[6]).first()
        mappings['f'] = (digits[1] - mappings['c']!!).first()
        digits[5] = sources.filter { it.size == 5 }
          .first { (digits[6] - it).size == 1 }
        mappings['e'] = (digits[6] - digits[5]).first()
        digits[9] = sources.filter { it.size == 6 }
          .first { (it + mappings['e']).size == 7 }
        digits[0] = sources.filter { it.size == 6 }
          .first { it != digits[9] && it != digits[6] }
        mappings['d'] = (digits[8] - digits[0]).first()
        mappings['g'] = digits[8].first { char ->
          val noG = setOf(digits[1], digits[4], digits[7])
          (sources - noG).all { char in it } && noG.none { char in it }
        }
        mappings['b'] = digits[8].first { it !in mappings.values }
        
        val reverse = sets.mapKeys { (key, _) -> key.map { mappings[it]!! }.toSet() }

        return@sumOf fourLine.split(" ")
          .joinToString("") { digit -> reverse[digit.toSet()].orEmpty() }
          .toInt()
      }
  }
}

fun main() = SomeDay.mainify(Day8)
