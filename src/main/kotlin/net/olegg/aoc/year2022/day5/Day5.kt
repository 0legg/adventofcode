package net.olegg.aoc.year2022.day5

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2022.DayOf2022

/**
 * See [Year 2022, Day 5](https://adventofcode.com/2022/day/5)
 */
object Day5 : DayOf2022(5) {
  private val NUM_PATTERN = "(\\d)".toRegex()
  private val COMMAND_PATTERN = "move (\\d+) from (\\d+) to (\\d+)".toRegex()

  override fun first(): Any? {
    val (stacksData, commandsData) = data.split("\n\n")

    val stackPositions = stacksData
      .lines()
      .last()
      .let { line ->
        val columns = NUM_PATTERN.findAll(line)
        columns.map {
          it.value.toInt() to it.range.first
        }
      }

    val stacks = stackPositions.associate { it.first to ArrayDeque<Char>() }
    stacksData
      .lines()
      .dropLast(1)
      .forEach { line ->
        stackPositions.forEach { (value, position) ->
          if (line[position] != ' ') {
            stacks.getValue(value).addLast(line[position])
          }
        }
      }

    val commands = commandsData
      .lines()
      .mapNotNull { COMMAND_PATTERN.find(it) }
      .map { line -> line.destructured.toList().map { it.toInt() } }
      .map { Triple(it[0], it[1], it[2]) }

    commands.forEach { (count, from, to) ->
      val fromStack = stacks.getValue(from)
      val toStack = stacks.getValue(to)
      repeat(count) {
        toStack.addFirst(fromStack.removeFirst())
      }
    }

    return stacks
      .mapValues { it.value.first() }
      .toList()
      .sortedBy { it.first }
      .map { it.second }
      .joinToString(separator = "") { it.toString() }
  }

  override fun second(): Any? {
    val (stacksData, commandsData) = data.split("\n\n")

    val stackPositions = stacksData
      .lines()
      .last()
      .let { line ->
        val columns = NUM_PATTERN.findAll(line)
        columns.map {
          it.value.toInt() to it.range.first
        }
      }

    val stacks = stackPositions.associate { it.first to ArrayDeque<Char>() }
    stacksData
      .lines()
      .dropLast(1)
      .forEach { line ->
        stackPositions.forEach { (value, position) ->
          if (line[position] != ' ') {
            stacks.getValue(value).addLast(line[position])
          }
        }
      }

    val commands = commandsData
      .lines()
      .mapNotNull { COMMAND_PATTERN.find(it) }
      .map { line -> line.destructured.toList().map { it.toInt() } }
      .map { Triple(it[0], it[1], it[2]) }

    commands.forEach { (count, from, to) ->
      val fromStack = stacks.getValue(from)
      val toStack = stacks.getValue(to)
      val stack = fromStack.take(count).asReversed()
      repeat(count) {
        fromStack.removeFirst()
        toStack.addFirst(stack[it])
      }
    }

    return stacks
      .mapValues { it.value.first() }
      .toList()
      .sortedBy { it.first }
      .map { it.second }
      .joinToString(separator = "") { it.toString() }
  }
}

fun main() = SomeDay.mainify(Day5)
