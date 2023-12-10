package net.olegg.aoc.year2023.day8

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2023.DayOf2023

/**
 * See [Year 2023, Day 8](https://adventofcode.com/2023/day/8)
 */
object Day8 : DayOf2023(8) {
  private val PATTERN = "(\\w+) = \\((\\w+), (\\w+)\\)".toRegex()

  override fun first(): Any? {
    val (instruction, nodeStrings) = data.split("\n\n")

    val nodes = nodeStrings.lineSequence()
      .mapNotNull { PATTERN.find(it) }
      .associate {
        it.groupValues[1] to mapOf('L' to it.groupValues[2], 'R' to it.groupValues[3])
      }

    return generateSequence(0) { it + 1 }
      .runningFold("AAA" to 0) { (curr, steps), index ->
        val char = instruction[index % instruction.length]
        nodes.getValue(curr).getValue(char) to steps + 1
      }
      .first { it.first == "ZZZ" }
      .second
  }

  override fun second(): Any? {
    val (instruction, nodeStrings) = data.split("\n\n")

    val nodes = nodeStrings.lineSequence()
      .mapNotNull { PATTERN.find(it) }
      .associate {
        it.groupValues[1] to mapOf('L' to it.groupValues[2], 'R' to it.groupValues[3])
      }

    val aNodes = nodes.keys.filter { it.endsWith("A") }

    val loops = aNodes.map { node ->
      generateSequence(0L) { it + 1 }
        .runningFold(node to 0L) { (curr, steps), index ->
          val char = instruction[(index % instruction.length).toInt()]
          nodes.getValue(curr).getValue(char) to steps + 1
        }
        .first { it.first.endsWith("Z") }
        .second
    }

    return loops.map { it / instruction.length }
      .fold(instruction.length.toLong(), Long::times)
  }
}

fun main() = SomeDay.mainify(Day8)
