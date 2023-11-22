package net.olegg.aoc.year2017.day25

import net.olegg.aoc.someday.SomeDay
import net.olegg.aoc.year2017.DayOf2017

/**
 * See [Year 2017, Day 25](https://adventofcode.com/2017/day/25)
 */
object Day25 : DayOf2017(25) {
  private val HEADER_PATTERN = (
    "Begin in state ([A-Z]+)\\.\\n" +
      "Perform a diagnostic checksum after ([0-9]+) steps\\."
    ).toRegex()
  private val STATE_PATTERN = "In state ([A-Z]+):".toRegex()
  private val ACTION_PATTERN = (
    "If the current value is ([0-9]+)[^-]+" +
      "- Write the value ([0-9]+)[^-]+" +
      "- Move one slot to the ([a-z]+)[^-]+" +
      "- Continue with state ([A-Z]+)\\."
    ).toRegex()

  override fun first(): Any? {
    val sections = data.split("\n\n")

    val (initialState, iterations) = HEADER_PATTERN.find(sections[0])?.destructured ?: error("Unable to parse")

    val states = sections
      .drop(1)
      .map { section ->
        State(
          name = STATE_PATTERN.find(section)?.destructured?.component1() ?: "A",
          actions = ACTION_PATTERN
            .findAll(section, section.indexOf("\n"))
            .map { match ->
              val (value, write, shift, state) = match.destructured
              value.toInt() to Action(write.toInt(), if (shift == "left") -1 else 1, state)
            }
            .toMap(),
        )
      }
      .associateBy { it.name }

    val tape = mutableMapOf<Int, Int>()

    (0..<iterations.toInt())
      .fold(initialState to 0) { (state, shift), _ ->
        states[state]
          ?.actions
          ?.get(tape[shift] ?: 0)
          ?.also { action -> tape[shift] = action.write }
          ?.let { action -> action.state to shift + action.shift }
          ?: (state to shift)
      }

    return tape.values.sum()
  }

  data class State(
    val name: String,
    val actions: Map<Int, Action>
  )

  data class Action(
    val write: Int,
    val shift: Int,
    val state: String
  )
}

fun main() = SomeDay.mainify(Day25)
