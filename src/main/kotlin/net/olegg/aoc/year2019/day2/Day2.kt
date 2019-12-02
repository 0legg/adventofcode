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
    val output = runProgram(program)

    return output[0]
  }

  override fun second(data: String): Any? {
    val program = data
        .trim()
        .parseInts(",")
        .toIntArray()

    for (noun in 0..99) {
      for (verb in 0..99) {
        program[1] = noun
        program[2] = verb
        if (runProgram(program)[0] == 19690720) {
          return noun * 100 + verb
        }
      }
    }

    return -1
  }

  private fun runProgram(input: IntArray): IntArray {
    val program = input.copyOf()
    var position = 0
    while (position in program.indices) {
      when (program[position]) {
        1 -> program[program[position + 3]] = program[program[position + 1]] + program[program[position + 2]]
        2 -> program[program[position + 3]] = program[program[position + 1]] * program[program[position + 2]]
        99 -> return program
        else -> throw IllegalStateException()
      }
      position += 4
    }

    return IntArray(0)
  }
}

fun main() = SomeDay.mainify(Day2)
