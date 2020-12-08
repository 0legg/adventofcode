package net.olegg.aoc.year2020.day8

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 8](https://adventofcode.com/2020/day/8)
 */
object Day8 : DayOf2020(8) {
  override fun first(data: String): Any? {
    val program = data
      .trim()
      .lines()
      .map { it.split(" ").toPair() }
      .map { it.first to it.second.toInt() }

    var position = 0
    var acc = 0
    val visited = mutableSetOf<Int>()
    while (position !in visited) {
      visited += position
      val (op, add) = program[position])
      when (op) {
        "nop" -> position++
        "acc" -> {
          acc += add
          position++
        }
        "jmp" -> position += add
      }
    }

    return acc
  }
}

fun main() = SomeDay.mainify(Day8)
