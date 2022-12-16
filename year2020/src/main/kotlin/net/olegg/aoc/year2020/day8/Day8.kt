package net.olegg.aoc.year2020.day8

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.utils.toPair
import net.olegg.aoc.year2020.DayOf2020

/**
 * See [Year 2020, Day 8](https://adventofcode.com/2020/day/8)
 */
object Day8 : DayOf2020(8) {
  override fun first(): Any? {
    val program = lines
      .map { it.split(" ").toPair() }
      .map { it.first to it.second.toInt() }

    var position = 0
    var acc = 0
    val visited = mutableSetOf<Int>()
    while (position !in visited) {
      visited += position
      val (op, add) = program[position]
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

  override fun second(): Any? {
    val program = lines
      .map { it.split(" ").toPair() }
      .map { it.first to it.second.toInt() }

    program.forEachIndexed { line, (op, add) ->
      if (op != "acc") {
        val newOp = if (op == "jmp") "nop" else "jmp"
        val newPair = newOp to add
        val newProgram = program.mapIndexed { index, pair -> if (index == line) newPair else pair }

        var position = 0
        var acc = 0
        val visited = mutableSetOf<Int>()
        while (position !in visited && position in newProgram.indices) {
          visited += position
          val (currOp, currAdd) = newProgram[position]
          when (currOp) {
            "nop" -> position++
            "acc" -> {
              acc += currAdd
              position++
            }
            "jmp" -> position += currAdd
          }
        }

        if (position !in newProgram.indices) {
          return acc
        }
      }
    }

    return 0
  }
}

fun main() = SomeDay.mainify(Day8)
