package net.olegg.aoc.year2019.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2019.DayOf2019

/**
 * See [Year 2019, Day 2](https://adventofcode.com/2019/day/2)
 */
object Day2 : DayOf2019(2) {
  override fun first(data: String): Any? {
    val program = data
        .trim()
        .parseInts(",")
        .toIntArray()
    program[1] = 12
    program[2] = 2

    var position = 0
    while (position in program.indices) {
      when (program[position]) {
        1 -> program[program[position + 3]] = program[program[position + 1]] + program[program[position + 2]]
        2 -> program[program[position + 3]] = program[program[position + 1]] * program[program[position + 2]]
        99 -> return program[0]
        else -> throw IllegalStateException()
      }
      position += 4
    }

    return 0
  }
}

fun main() = SomeDay.mainify(Day2)
