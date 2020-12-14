package net.olegg.aoc.year2020.day14

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 14](https://adventofcode.com/2020/day/14)
 */
object Day14 : DayOf2020(14) {
  private val PATTERN = "^mem\\[(\\d+)] = (\\d+)$".toRegex()
  override fun first(data: String): Any? {
    val memory = mutableMapOf<Long, Long>()
    var mask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

    data.trim().lines().forEach { line ->
      when {
        line.startsWith("mask") -> {
          mask = line.split(" = ").last()
        }
        line.startsWith("mem") -> {
          PATTERN.findAll(line).forEach {
            val (address, value) = it.groupValues.drop(1).mapNotNull { token -> token.toLongOrNull() }
            val bitValue = value.toString(2).padStart(36, '0')
            memory[address] = bitValue.zip(mask) { a, b -> if (b == 'X') a else b }
              .joinToString(separator = "")
              .toLong(2)
          }
        }
      }
    }

    return memory.values.sum()
  }
}

fun main() = SomeDay.mainify(Day14)
