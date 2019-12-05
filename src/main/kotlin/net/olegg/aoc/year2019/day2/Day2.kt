package net.olegg.aoc.year2019.day2

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.parseInts
import net.olegg.aoc.year2019.DayOf2019
import net.olegg.aoc.year2019.Intcode

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
    Intcode.eval(program)

    return program[0]
  }

  override fun second(data: String): Any? {
    val program = data
        .trim()
        .parseInts(",")
        .toIntArray()

    for (noun in 0..99) {
      for (verb in 0..99) {
        val newProgram = program.copyOf()
        newProgram[1] = noun
        newProgram[2] = verb
        Intcode.eval(newProgram)
        if (newProgram[0] == 19690720) {
          return noun * 100 + verb
        }
      }
    }

    return -1
  }
}

fun main() = SomeDay.mainify(Day2)
