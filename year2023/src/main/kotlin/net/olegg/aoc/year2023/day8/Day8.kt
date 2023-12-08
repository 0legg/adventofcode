package net.olegg.aoc.year2023.day8

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 8](https://adventofcode.com/2023/day/8)
 */
object Day8 : DayOf2023(8) {
  private val PATTERN = "(\\w+) = \\((\\w+), (\\w+)\\)".toRegex()
  private val DIRS = "LR"

  override fun first(): Any? {
    val (instruction, nodeStrings) = data.split("\n\n")

    val nodes = nodeStrings.lineSequence()
      .mapNotNull { PATTERN.find(it) }
      .associate {
        it.groupValues[1] to (it.groupValues[2] to it.groupValues[3])
      }

    return generateSequence(0) { it + 1 }
      .runningFold("AAA" to 0) { (curr, steps), index ->
        val char = instruction[index % instruction.length]
        val direction = DIRS.indexOf(char)
        nodes.getValue(curr).toList()[direction] to steps + 1
      }
      .first { it.first == "ZZZ" }
      .second
  }
}

fun main() = SomeDay.mainify(Day8)
